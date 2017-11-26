<template>
<section>

  <el-input placeholder="请输入内容" v-model="key" class="link-search" @change="filterLink">
    <!-- <el-button slot="append" icon="el-icon-search"></el-button> -->
    <el-button slot="append" type="primary" @click="showAdd"><i class="fa fa-plus"></i></el-button>
  </el-input>
  <div class="link-row">
    <el-checkbox-group v-model="selectedTags" size="small" @change="filterTag">
      <span v-for="tag in tags" style="margin-right:10px;">
        <el-checkbox  :label="tag.id" border>  <el-tag size="mini" :type="tag.color">{{tag.name}}</el-tag></el-checkbox>
      </span>
    </el-checkbox-group>
    <div class="" style="min-width:200px">
      <el-button class="button-new-tag" size="small" @click="showAddTag"><i class="fa fa-plus"></i> New Tag</el-button>
      <el-button class="button-new-tag" size="small" type="danger" @click="deleteTag" :disabled="selectedTags.length==0"><i class="fa fa-remove"></i> Delete Tag</el-button>

    </div>

  </div>
  <template v-for="item in links">
			<link-item :link="item" @remove="removeLink" @edit="editLink"></link-item>
		</template>
  <!-- link -->
  <el-dialog title="Add a new link" :visible.sync="dialogVisible" width="30%">
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
              <el-checkbox style="margin-right:10px" :label="tag.id">{{tag.name}}</el-checkbox>
            </span>
        </el-checkbox-group>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
				<el-button @click="dialogVisible = false">Cancel</el-button>
				<el-button type="primary" @click="add">Confirm</el-button>
			</span>
  </el-dialog>

  <!-- Add a tag -->
  <el-dialog title="Add a new link" :visible.sync="tagVisible" width="20%">

    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="Name">
        <el-input v-model="formTag.name"></el-input>
      </el-form-item>
      <el-form-item label="Type">
        <el-radio-group v-model="formTag.color">
          <el-radio label="info"></el-radio>
          <el-radio label="success"></el-radio>
          <el-radio label="warning"></el-radio>
          <el-radio label="danger"></el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
				<el-button @click="tagVisible = false">Cancel</el-button>
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
  delTag,
} from "../../api/api";
import linkItem from "@/components/linkItem";
export default {
  data() {
    return {
      tagVisible: false,
      dialogVisible: false,
      key: "",
      all: [],
      links: [],
      form: {
        name: "",
        href: "",
        tags: [],
      },
      formTag: {
        name: '',
        color: '',
      },
      tags: [],
      selectedTags: [],
    };
  },
  components: {
    linkItem
  },
  methods: {
    deleteTag() {
      delTag(this.selectedTags).then(res => {
        if (res.status == 200) {
          console.log(res)
          this.getTags();
          this.getLinks();
          this.$notify({
            title: 'Success',
            message: 'Deleted',
            type: 'success'
          });
          this.selectedTags = []
        } else {
          this.$this.$notify.error({
            title: 'Error',
            message: res.statusText,
          });
        }

      })
    },
    editLink(link) {
      console.log('edit', link)
      this.dialogVisible = true;
      this.form = {
        id: link.id,
        name: link.name,
        href: link.href,
        tags: link.tags.map(it => it.id),
      }
    },
    addTagConfirm() {
      addTag(this.formTag).then(res => {
        this.getTags();
        console.log(res)
        this.tagVisible = false;
      })
    },
    filterTag(tagIds) {
      // console.log(tagIds)
      if (!tagIds || tagIds.length == 0) {
        this.links = this.all;
        return;
      }
      var contain = link => {
        return tagIds.some(it => link.tags.some(tag => tag.id == it))
      }
      this.links = this.all.filter(it => contain(it));
    },
    showAddTag() {
      this.tagVisible = true;

    },
    filterLink(value) {
      this.links = this.all.filter(it => it.name.toLowerCase().indexOf(value.toLowerCase()) > -1);
      // console.log(value,this.links)
    },
    getLinks() {
      getLinkList().then(res => {
        console.log(res);
        this.links = res.data;
        this.all = res.data;
      });
    },
    showAdd() {
      this.dialogVisible = true;
      this.form = {
        name: "",
        href: "",
        tags: [],
      }
    },
    add() {
      var tagIds = this.form.tags.map(function(it) {
        return {
          id: it
        };
      });
      var params = Object.assign({}, this.form);
      params.tags = tagIds;
      addLink(params).then(res => {
        console.log(res);
        this.dialogVisible = false;
        this.getLinks();
      });
    },
    removeLink(id) {
      var _this = this;
      this.$confirm("Are you sure to delete this item?", "Confirm", {
          confirmButtonText: "OK",
          cancelButtonText: "Cancel",
          type: "warning"
        })
        .then(() => {
          delLink(id).then(res => {
            console.log(res);
            _this.getLinks();
            _this.$message({
              type: "success",
              message: "Deleted!"
            });
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "Cancel"
          });
        });
    },
    getTags() {
      getTags().then(res => {
        this.tags = res.data;
      })
    }
  },
  created() {
    this.getLinks();
    this.getTags();
  }
};
</script>

<style scoped>
.link-search {
  margin: 10px 0;
}

.link-row {
  display: flex;
  justify-content: space-between;
}
</style>
