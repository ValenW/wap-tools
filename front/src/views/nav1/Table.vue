<template>
<section>
    <!--工具条-->
    <el-col :span="24" style="padding: 20px 0px 0px;">
        <el-form :inline="true" >
            <el-form-item>
                <el-input v-model="keyword" placeholder="keyword" @input="search"></el-input>
            </el-form-item>
            <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
            <el-button type="success" @click="downloadTxt" :disabled="this.sels.length===0">下载</el-button>
            <!-- <el-form-item>
					<el-button type="primary" v-on:click="getTexts">查询</el-button>
				</el-form-item> -->
            <!-- <el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item> -->
        </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="texts" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;" class="table-small">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column type="index" width="60" :index="indexMethod">
        </el-table-column>
        <el-table-column prop="id" label="id" width="400" sortable>
        </el-table-column>
        <el-table-column prop="en" label="English" width="250" sortable>
        </el-table-column>
        <el-table-column prop="ja" label="Japanese" width="250" sortable>
        </el-table-column>
        <el-table-column prop="createTime" label="Create Time" width="200" sortable>
        </el-table-column>
        <el-table-column prop="updateTime" label="Last Update Time" width="200" sortable>
        </el-table-column>
        <el-table-column label="操作" width="150">
            <template scope="scope">
					<el-button type="text" size="medium" @click="handleEdit(scope.$index, scope.row)"><i class="fa fa-edit"></i></el-button>
					<el-button type="text" size="medium" @click="handleDel(scope.$index, scope.row)"><i class="fa fa-remove"></i></el-button>
				</template>
        </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" style="margin-top:10px">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>
    </el-col>

    <!--编辑界面-->
    <el-dialog title="编辑" :visible.sync="editFormVisible" :close-on-click-modal="false">
        <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
            <el-form-item label="id" prop="id">
                <el-input v-model="editForm.id" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="en" prop="en">
                <el-input v-model="editForm.en" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="ja" prop="ja">
                <el-input v-model="editForm.ja" auto-complete="off"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click.native="editFormVisible = false">取消</el-button>
            <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
        </div>
    </el-dialog>


</section>
</template>

<script>
import util from '../../common/js/util'
// import NProgress from 'nprogress'
import {
  getTextsapi,
  delTextapi,
  bulkDelTxt,
  saveTextapi,
  addUser
} from '../../api/api'

export default {
  data() {
    return {
      keyword: '',
      texts: [],
      total: 0,
      page: 1,
      pageSize: 20,
      listLoading: false,
      sels: [], // 列表选中列

      editFormVisible: false, // 编辑界面是否显示
      editLoading: false,
      editFormRules: {
        name: [{
          required: true,
          message: '请输入姓名',
          trigger: 'blur'
        }]
      },
      // 编辑界面数据
      editForm: {
        id: 0,
        name: '',
        sex: -1,
        age: 0,
        birth: '',
        addr: ''
      },
      addFormVisible: false, // 新增界面是否显示
      addLoading: false,
      addFormRules: {
        name: [{
          required: true,
          message: '请输入姓名',
          trigger: 'blur'
        }]
      },
      // 新增界面数据
      addForm: {
        name: '',
        sex: -1,
        age: 0,
        birth: '',
        addr: ''
      }

    }
  },
  computed: {

  },
  methods: {
    search(val, a) {
      console.log(val, a)
      this.page = 1
      this.total = 0
      this.getTexts()
    },
    indexMethod(index) {
      return (this.page - 1) * this.pageSize + index + 1
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.getTexts()
    },
    handleCurrentChange(val) {
      this.page = val
      this.getTexts()
    },
    // 获取用户列表
    getTexts() {
      this.listLoading = true
      // NProgress.start();
      var params = { params: {
        keyword: this.keyword,
        curPage: this.page,
        pageSize: this.pageSize
      }}
      getTextsapi(params).then((res) => {
        this.total = res.data.size
        this.texts = res.data.data
        this.listLoading = false
        // NProgress.done();
      })
    },
    // 删除
    handleDel: function(index, row) {
      this.$confirm('确认删除该记录吗?', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true
        // NProgress.start();
        const para = {
          params: {
            id: row.id
          }
        }
        delTextapi(para).then((res) => {
          this.listLoading = false
          // NProgress.done();
          this.$message({
            message: '删除成功',
            type: 'success'
          })
          this.getTexts()
        })
      }).catch(() => {

      })
    },
    // 显示编辑界面
    handleEdit: function(index, row) {
      this.editFormVisible = true
      this.editForm = Object.assign({}, row)
    },
    // 显示新增界面
    handleAdd: function() {
      this.addFormVisible = true
      this.addForm = {
        name: '',
        sex: -1,
        age: 0,
        birth: '',
        addr: ''
      }
    },
    // 编辑
    editSubmit: function() {
      this.$refs.editForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.editLoading = true
            // NProgress.start();
            const para = Object.assign({}, this.editForm)
            para.birth = (!para.birth || para.birth === '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd')
            saveTextapi([para]).then((res) => {
              this.editLoading = false
              // NProgress.done();
              this.$message({
                message: '提交成功',
                type: 'success'
              })
              this.$refs['editForm'].resetFields()
              this.editFormVisible = false
              this.getTexts()
            })
          })
        }
      })
    },
    // 新增
    addSubmit: function() {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.addLoading = true
            // NProgress.start();
            const para = Object.assign({}, this.addForm)
            para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd')
            addUser(para).then((res) => {
              this.addLoading = false
              // NProgress.done();
              this.$message({
                message: '提交成功',
                type: 'success'
              })
              this.$refs['addForm'].resetFields()
              this.addFormVisible = false
              this.getTexts()
            })
          })
        }
      })
    },
    selsChange: function(sels) {
      this.sels = sels
    },
    // 批量删除
    batchRemove: function() {
      var ids = this.sels.map(item => item.id)
      this.$confirm('确认删除选中记录吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true
        // NProgress.start();
        bulkDelTxt(ids).then((res) => {
          this.listLoading = false
          // NProgress.done();
          this.$message({
            message: '删除成功',
            type: 'success'
          })
          this.getTexts()
        })
      }).catch(() => {

      })
    },
    downloadTxt() {
      // console.log(this.sels);
      var str = util.genText(this.sels)
      util.download(str, util.getFileName())
    }
  },
  mounted() {
    this.getTexts()
  }
}
</script>

<style lan="scss">
.table-small td {
    padding: 0px;
}
</style>
