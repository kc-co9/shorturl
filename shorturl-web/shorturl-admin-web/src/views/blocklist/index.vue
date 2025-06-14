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
        <el-form-item label="状态：" prop="serverCode">
          <el-select v-model="dataForm.status" filterable placeholder="状态">
            <el-option label="全部" value=""/>
            <el-option v-for="(value, key) in statusEnum" :key="key" :label="value" :value="key"/>
          </el-select>
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
        共搜到{{ dataTable.totalCount }}条数据
      </h4>
      <el-table
          :data="dataTable.records"
          border
          style="width: 100%">

        <el-table-column
            prop="blockLink"
            label="被禁链接">
        </el-table-column>
        <el-table-column
            prop="remark"
            label="备注">
        </el-table-column>
        <el-table-column
            label="状态">
          <template slot-scope="scope">
            {{ statusEnum[scope.row.status] }}
          </template>
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
          </template>
        </el-table-column>
      </el-table>
    </el-row>
    <el-row type="flex" justify="center" style="margin-top:20px">
      <el-pagination
          :current-page="parseInt(dataTable.paging.pageNo)"
          :total="parseInt(dataTable.totalPages)"
          layout="sizes,total, prev, pager, next, jumper"
          @current-change="pageChange"
      />
    </el-row>

    <el-dialog title="添加黑名单" :visible.sync="addVisible">
      <el-form :model="addForm" label-position="right" label-width="120px">
        <el-form-item label="链接">
          <el-input v-model="addForm.blockLink" placeHolder="请输入" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="addForm.remark" placeHolder="请输入" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="addForm.status" filterable placeholder="状态">
            <el-option v-for="(value, key) in statusEnum" :key="key" :label="value" :value="key"/>
          </el-select>
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

    <el-dialog title="编辑短链" :visible.sync="editVisible">
      <el-form :model="editForm" label-position="right" label-width="120px">
        <el-form-item label="备注">
          <el-input v-model="editForm.remark" placeHolder="请输入" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="editForm.status" filterable placeholder="状态">
            <el-option v-for="(value, key) in statusEnum" :key="key" :label="value" :value="key"/>
          </el-select>
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
import {getBlocklistList, addBlocklist, updateBlocklist, removeBlocklist} from '@/api/blocklist'
import {updateShorturl} from "@/api/shorturl";

export default {
  name: 'Server',
  data() {
    return {
      dataForm: {
        status: "",
        pageNo: 1,
        pageSize: 10
      },
      dataTable: {
        totalCount: 0,
        totalPages: 10,
        paging: {
          pageNo: 1,
          pageSize: 10
        },
        records: []
      },
      tableLoading: false,
      addVisible: false,
      addForm: {
        blockLink: null,
        remark: null,
        status: null,
      },
      editVisible: false,
      editForm: {
        blockId: null,
        blockLink: null,
        remark: null,
        status: null,
      },
      statusEnum: {
        0: '已失效',
        1: '已激活',
      }
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
      this.dataForm.status = "";
    },
    async pageData() {
      this.tableLoading = true
      getBlocklistList(this.dataForm)
          .then((response) => {
            if (response.code === CONSTANTS.SUCCESS_CODE) {
              this.dataTable = response.data
            }
            this.tableLoading = false
          })
          .catch((err) => {
            this.tableLoading = false
            console.error(err)
          })
    },
    showAdd() {
      this.addForm.blockLink = null
      this.addForm.remark = null
      this.addForm.status = null
      this.addVisible = true;
    },
    add() {
      this.$confirm('此操作将新增黑名单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        addBlocklist(this.addForm)
            .then((response) => {
              if (response.code === CONSTANTS.SUCCESS_CODE) {
                this.$message({
                  type: 'success',
                  message: '新增成功!'
                });
                this.search();
                this.addVisible = false
                this.addForm.blockLink = null
                this.addForm.status = null
                this.addForm.remark = null
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
      this.editForm.blockId = row.blockId
      this.editForm.remark = row.remark
      this.editForm.status = row.status + ''
      this.editVisible = true
    },
    edit() {
      this.$confirm('此操作将更新黑名单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateBlocklist(this.editForm)
            .then((response) => {
              if (response.code === CONSTANTS.SUCCESS_CODE) {
                this.$message({
                  type: 'success',
                  message: '更新成功!'
                });
                this.search();
                this.editVisible = false
                this.editForm.blockId = null
                this.editForm.remark = null
                this.editForm.status = null
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
      removeBlocklist({blockId: row.blockId})
          .then((response) => {
            if (response.code === CONSTANTS.SUCCESS_CODE) {
              this.search();
            }
          })
          .catch((err) => {
            console.error(err)
          })
    },
  }
}
</script>