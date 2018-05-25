<template>
<section>

  <el-input placeholder="请输入内容" v-model="key" class="link-search" >
    <!-- <el-button slot="append" icon="el-icon-search"></el-button> -->
    <el-button slot="append" type="primary" @click="showAdd"><i class="fa fa-plus"></i></el-button>
  </el-input>
  <div class="link-row">
    <el-checkbox-group v-model="selectedTags" size="small" >
      <span v-for="tag in tags" style="margin-right:10px;margin-bottom:5px">
        <el-checkbox style="margin-bottom:5px;" :label="tag.id" >  <el-tag size="mini" :color="tag.color" style="color:#fff">{{tag.name}}</el-tag></el-checkbox>
        <!-- <el-checkbox  :label="tag.id" border size="medium">  {{tag.name}}</el-checkbox> -->
      </span>
    </el-checkbox-group>
    <div class="" style="min-width:140px">
      <el-button-group>
        <el-button class="button-new-tag" size="small" @click="showAddTag"><i class="fa fa-plus"></i></el-button>
        <el-button class="button-new-tag" size="small" @click="showEditTag" :disabled="selectedTags.length!=1"><i class="fa fa-edit"></i></el-button>
        <el-button class="button-new-tag" size="small" type="danger" @click="deleteTag" :disabled="selectedTags.length==0"><i class="fa fa-remove"></i></el-button>
      </el-button-group>
    </div>
  </div>
  <br>
  <template v-for="item in links">
	<link-item :link="item" @remove="removeLink" @edit="editLink"></link-item>
  </template>

  <!-- Add or update a link -->
  <el-dialog :title="linkDialogTitle" :visible.sync="linkDialogVisible" width="30%">
    <el-form ref="form" :model="form" label-width="80px">

      <el-form-item label="Name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>

      <el-form-item label="Href">
        <el-input type="textarea" v-model="form.href"></el-input>
      </el-form-item>
      <el-form-item label="Tags">
        <el-checkbox-group v-model="form.tags">
          <span v-for="tag in tags">
              <el-checkbox style="margin-right:10px;" :label="tag.id">
                <el-tag size="mini" :color="tag.color" style="color:#fff">{{tag.name}}</el-tag>
              </el-checkbox>
          </span>
        </el-checkbox-group>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
				<el-button @click="linkDialogVisible = false">Cancel</el-button>
				<el-button type="primary" @click="add" :loading="linkSaving">Confirm</el-button>
			</span>
  </el-dialog>

  <!-- Add or update a tag -->
  <el-dialog :title="tagDialogTitle" :visible.sync="tagDialogVisible" width="20%">

    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="Name">
        <el-input v-model="formTag.name"></el-input>
      </el-form-item>
      <el-form-item label="Color" style="margin-bottom: 0px">
        <el-color-picker v-model="formTag.color" @active-change="colorChange" show-alpha></el-color-picker>
        <el-tooltip class="random-color" effect="dark" content="Set random color" placement="right">
         <div style='display:inline-block'>
             <el-button @click="setRandomColor" size="small"><i class="fa fa-exchange"></i></el-button>
         </div>
         </el-tooltip>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
				<el-button @click="tagDialogVisible = false">Cancel</el-button>
				<el-button type="primary" @click="addTagConfirm">Confirm</el-button>
			</span>
  </el-dialog>

</section>
</template>

<script>
import {
  getLinkList,
  addLink,
  delLink,
  getTags,
  addTag,
  delTag
} from '../../api/api'
import linkItem from '@/components/linkItem'
export default {
  data() {
    return {
      tagDialogVisible: false,
      linkDialogVisible: false,
      linkDialogTitle:'',
      tagDialogTitle:'',
      key: '',
      all: [],
      form: {
        name: '',
        href: '',
        tags: []
      },
      formTag: {
        id: '',
        name: '',
        color: ''
      },
      tags: [],
      selectedTags: [],
      linkSaving: false
    }
  },
  components: {
    linkItem
  },
  computed: {
    links() {
      return this.all.filter(item => {
        if (!this.selectedTags || this.selectedTags.length === 0) {
          return true
        }
        return this.selectedTags.every(it => item.tags.some(tag => tag.id === it))
      }).filter(item => {
        return item.name.toLowerCase().indexOf(this.key.toLowerCase()) > -1
      })
    }

  },
  methods: {
    colorChange(color) {
      this.formTag.color = color
    },
    deleteTag() {
      delTag(this.selectedTags).then(res => {
        if (res.status === 200) {
          console.log(res)
          this.getTags()
          this.triggerUpdateLink()

          this.$notify({
            title: 'Success',
            message: 'Deleted',
            type: 'success'
          })
          this.selectedTags = []
        } else {
          this.$this.$notify.error({
            title: 'Error',
            message: res.statusText
          })
        }
      })
    },
    editLink(link) {
      console.log('edit', link)
      this.linkDialogVisible = true
      this.linkDialogTitle = "Edit link"
      this.form = {
        id: link.id,
        name: link.name,
        href: link.href,
        tags: link.tags.map(it => it.id)
      }
    },
    addTagConfirm() {
      var isAddNew = (this.formTag.id == '');
      addTag(this.formTag).then(res => {
        this.getTags()
        console.log(res)
        console.log((isAddNew ? 'add tag status: ' : 'update tag status: ') + res.status)
        this.tagDialogVisible = false
        this.getLinks()
        if (!isAddNew) {
            // Trick: update the links which used the modified tag
            this.triggerUpdateLink()
        }
      })
    },

    setRandomColor() {
      this.formTag.color = '#'+Math.floor(Math.random()*0xFFFFFF).toString(16);
    },

    showAddTag() {
      this.formTag = {
          id: '',
          name: '',
          color: ''
      }
      this.tagDialogVisible = true
      this.tagDialogTitle = "Add a new tag"
      this.setRandomColor();
    },

    showEditTag() {
      this.formTag.id = this.selectedTags[0]
      const tag = this.tags.filter(item => item.id == this.formTag.id)[0]
      this.formTag.name = tag.name
      this.formTag.color = tag.color
      this.tagDialogVisible = true
      this.tagDialogTitle = "Edit tag"
    },

    // Trick: trigger update links after some event like modified one tag or delete tags
    triggerUpdateLink() {
        if (this.all.length > 0) {
            var link = this.all[0];
            this.form = {
              id: link.id,
              name: link.name,
              href: link.href,
              tags: link.tags.map(it => it.id)
            }
            this.add();
        }
    },

    getLinks() {
      getLinkList().then(res => {
        console.log(res)
        this.all = res.data
      })
    },
    showAdd() {
      this.linkDialogVisible = true
      this.linkDialogTitle = "Add a new link"
      this.form = {
        name: '',
        href: '',
        tags: []
      }
    },
    add() {
      var tagIds = this.form.tags.map(function(it) {
        return {
          id: it
        }
      })
      var params = Object.assign({}, this.form)
      params.tags = tagIds
      this.linkSaving = true
      addLink(params).then(res => {
        console.log(res)
        this.linkDialogVisible = false
        this.getLinks()
        this.linkSaving = false
      })
    },
    removeLink(id) {
      var _this = this
      this.$confirm('Are you sure to delete this item?', 'Confirm', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(() => {
          delLink(id).then(res => {
            console.log(res)
            _this.getLinks()
            _this.$message({
              type: 'success',
              message: 'Deleted!'
            })
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: 'Cancel'
          })
        })
    },
    getTags() {
      getTags().then(res => {
        this.tags = res.data
      })
    }
  },
  created() {
    this.getLinks()
    this.getTags()
  }
}
</script>

<style lan="scss">
.link-search {
  margin: 10px 0;
}

.link-row {
  display: flex;
  justify-content: space-between;
}

.random-color {
    vertical-align: top;
    margin-left: 8px;
}
</style>
