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
        <el-form-item label="短链CODE：" prop="serverId">
          <el-input v-model="dataForm.code" placeholder="请输入内容" clearable/>
        </el-form-item>
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
            prop="rawLink"
            label="原始链接">
        </el-table-column>
        <el-table-column
            prop="shortLink"
            label="短链">
        </el-table-column>
        <el-table-column
            label="状态">
          <template slot-scope="scope">
            {{ statusEnum[scope.row.status] }}
          </template>
        </el-table-column>
        <el-table-column
            label="有效期">
          <template slot-scope="scope">
            {{ scope.row.validTimeStart }} - {{ scope.row.validTimeEnd }}
          </template>
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

    <el-dialog title="创建短链" :visible.sync="addVisible">
      <el-form :model="addForm" label-position="right" label-width="120px">
        <el-form-item label="原始链接">
          <el-input v-model="addForm.rawLink" placeHolder="请输入" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="有效期">
          <el-date-picker
              v-model="addForm.validTimeRange"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd HH:mm:ss"
              @change="addFormvalidTimeRangeChange">
          </el-date-picker>
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
        <el-form-item label="原始链接">
          <el-input v-model="editForm.rawLink" placeHolder="请输入" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="editForm.status" filterable placeholder="状态">
            <el-option v-for="(value, key) in statusEnum" :key="key" :label="value" :value="key"/>
          </el-select>
        </el-form-item>
        <el-form-item label="有效期">
          <el-date-picker
              v-model="editForm.validTimeRange"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd HH:mm:ss"
              @change="editFormvalidTimeRangeChange">
          </el-date-picker>
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
import {getShorturlList, createShorturl, updateShorturl} from '@/api/shorturl'

export default {
  name: 'Shorturl',
  data() {
    return {
      dataForm: {
        code: "",
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
        rawLink: null,
        status: null,
        validTimeRange: null,
        validTimeStart: null,
        validTimeEnd: null
      },
      editVisible: false,
      editForm: {
        shortId: null,
        rawLink: null,
        status: null,
        validTimeRange: null,
        validTimeStart: null,
        validTimeEnd: null
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
      this.dataForm.code = "";
      this.dataForm.status = "";
    },
    async pageData() {
      this.tableLoading = true
      getShorturlList(this.dataForm)
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
      this.addForm.rawLink = null
      this.addForm.validTimeRange = null
      this.addForm.validTimeStart = null
      this.addForm.validTimeEnd = null
      this.addVisible = true;
    },
    add() {
      this.$confirm('此操作将新增短链, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        createShorturl(this.addForm)
            .then((response) => {
              if (response.code === CONSTANTS.SUCCESS_CODE) {
                this.$message({
                  type: 'success',
                  message: '新增成功!'
                });
                this.search();
                this.addVisible = false
                this.addForm.rawLink = null
                this.addForm.validTimeRange = null
                this.addForm.validTimeStart = null
                this.addForm.validTimeEnd = null
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
      this.editForm.shortId = row.shortId
      this.editForm.rawLink = row.rawLink
      this.editForm.status = row.status + ''
      this.editForm.validTimeStart = row.validTimeStart
      this.editForm.validTimeEnd = row.validTimeEnd
      this.editForm.validTimeRange = [row.validTimeStart, row.validTimeEnd]
      this.editVisible = true
    },
    edit() {
      this.$confirm('此操作将更新短链, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateShorturl(this.editForm)
            .then((response) => {
              if (response.code === CONSTANTS.SUCCESS_CODE) {
                this.$message({
                  type: 'success',
                  message: '更新成功!'
                });
                this.search();
                this.editVisible = false
                this.editForm.shortId = null
                this.editForm.rawLink = null
                this.editForm.status = null
                this.editForm.validTimeRange = null
                this.editForm.validTimeStart = null
                this.editForm.validTimeEnd = null
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
    addFormvalidTimeRangeChange(timeRange) {
      if (timeRange != null && timeRange.length === 2) {
        this.addForm.validTimeStart = timeRange[0]
        this.addForm.validTimeEnd = timeRange[1]
      }
    },
    editFormvalidTimeRangeChange(timeRange) {
      if (timeRange != null && timeRange.length === 2) {
        this.editForm.validTimeStart = timeRange[0]
        this.editForm.validTimeEnd = timeRange[1]
      }
    }
  }
}
</script>