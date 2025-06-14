<template>
  <div class="app-container">
    <el-row>

      <el-form
          ref="dataForm"
          inline
          :model="dataForm"
          type="flex"
          justify="center"
      >
        <el-form-item label="用户名：" prop="username">
          <el-input v-model="dataForm.username" placeholder="请输入内容" clearable/>
        </el-form-item>
        <el-form-item label="邮箱：" prop="email">
          <el-input v-model="dataForm.email" placeholder="请输入内容" clearable/>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="reset">
            重置
          </el-button>
          <el-button type="primary" @click="search">
            搜索
          </el-button>
          <el-button type="primary" @click="showAdd">
            新增
          </el-button>
        </el-form-item>
      </el-form>

    </el-row>

    <el-row>
      <h4 style="margin:0">
        共搜到{{ dataTable.total }}条数据
      </h4>
      <el-table
          :data="dataTable.records"
          border
          style="width: 100%">
        <el-table-column
            prop="administratorId"
            label="ID">
        </el-table-column>
        <el-table-column
            prop="username"
            label="用户名">
        </el-table-column>
        <el-table-column
            prop="email"
            label="邮箱">
        </el-table-column>
        <el-table-column
            prop="createTime"
            label="创建时间">
        </el-table-column>
        <el-table-column
            align="center"
            label="操作"
            fixed="right"
            width="120">
          <template slot-scope="scope">
            <el-button type="text" @click="showEdit(scope.row)">
              编辑
            </el-button>
            <el-button type="text" @click="remove(scope.row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-row>
    <el-row type="flex" justify="center" style="margin-top:20px">
      <el-pagination
          :current-page="parseInt(dataTable.current)"
          :total="parseInt(dataTable.total)"
          layout="sizes,total, prev, pager, next, jumper"
          @current-change="pageChange"
      />
    </el-row>

    <el-dialog title="新增管理员" :visible.sync="addVisible">
      <el-form :model="addForm" label-position="right" label-width="120px">
        <el-form-item label="用户名">
          <el-input v-model="addForm.username" placeHolder="请输入" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="addForm.email" placeHolder="请输入" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="addForm.password" placeHolder="请输入" style="width: 300px"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addVisible = false">
          取 消
        </el-button>
        <el-button type="primary" @click="add">
          确 定
        </el-button>
      </div>
    </el-dialog>

    <el-dialog title="编辑管理员" :visible.sync="editVisible">
      <el-form :model="editForm" label-position="right" label-width="120px">
        <el-form-item label="用户名">
          <el-input v-model="editForm.username" placeHolder="请输入" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="editForm.email" placeHolder="请输入" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="editForm.password" placeHolder="请输入" style="width: 300px"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editVisible = false">
          取 消
        </el-button>
        <el-button type="primary" @click="edit">
          确 定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>


<script>
import {CONSTANTS} from '@/api/common'
import {getAdministratorList, addAdministrator, updateAdministrator, removeAdministrator} from '@/api/administrator'
import {updateBlocklist} from "@/api/blocklist";

export default {
  name: 'Administrator',
  data() {
    return {
      dataForm: {
        username: "",
        email: "",
        pageNo: 1,
        pageSize: 10
      },
      dataTable: {
        current: 1,
        size: 10,
        total: 0,
        pages: 0,
        records: []
      },
      tableLoading: false,
      addVisible: false,
      addForm: {
        username: null,
        password: null,
        email: null
      },
      editVisible: false,
      editForm: {
        administratorId: null,
        username: null,
        password: null,
        email: null
      },
    }
  },
  mounted: function () {
    this.pageData();
  },
  methods: {
    search() {
      this.pageData();
    },
    pageChange(e) {
      this.dataForm.pageNo = e
      this.pageData();
    },
    reset() {
      this.dataForm.username = "";
      this.dataForm.email = "";
    },
    async pageData() {
      this.tableLoading = true
      getAdministratorList(this.dataForm)
          .then((response) => {
            if (response.code === CONSTANTS.SUCCESS_CODE) {
              this.dataTable = response.data
            }
            this.tableLoading = false
          })
          .catch((err) => {
            this.tableLoading = false
          })
    },
    showAdd() {
      this.addForm.username = null
      this.addForm.email = null
      this.addForm.password = null
      this.addVisible = true;
    },
    add() {
      this.$confirm('此操作将新增管理员, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        addAdministrator(this.addForm)
            .then((response) => {
              if (response.code === CONSTANTS.SUCCESS_CODE) {
                this.$message({
                  type: 'success',
                  message: '新增成功!'
                });
                this.search();
                this.addVisible = false
                this.addForm.username = null
                this.addForm.email = null
                this.addForm.password = null
              }
            })
            .catch((err) => {
              console.error(err)
              this.$message.error('新增异常，请稍后重试');
            })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消新增'
        });
      });
    },
    showEdit(row) {
      this.editForm.administratorId = row.administratorId
      this.editForm.username = row.username
      this.editForm.email = row.email
      this.editForm.password = "**********"
      this.editVisible = true
    },
    edit() {
      this.$confirm('此操作将更新管理员, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateAdministrator(this.editForm)
            .then((response) => {
              if (response.code === CONSTANTS.SUCCESS_CODE) {
                this.$message({
                  type: 'success',
                  message: '更新成功!'
                });
                this.search();
                this.editVisible = false
                this.editForm.administratorId = null
                this.editForm.username = null
                this.editForm.email = null
                this.editForm.password = null
              }
            })
            .catch((err) => {
              console.error(err)
              this.$message.error('更新异常，请稍后重试');
            })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消更新'
        });
      });
    },
    remove(row) {
      removeAdministrator({administratorId: row.administratorId})
          .then((response) => {
            if (response.code === CONSTANTS.SUCCESS_CODE) {
              this.search();
            }
          })
          .catch((err) => {
            console.error(err)
          })
    }
  }
}
</script>