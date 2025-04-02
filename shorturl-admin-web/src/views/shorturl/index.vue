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
        <el-form-item label="短链KEY：" prop="serverId">
          <el-input v-model="dataForm.key" placeholder="请输入内容" clearable/>
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
            prop="url"
            label="原始链接">
        </el-table-column>
        <el-table-column
            prop="shorturl"
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
            {{ scope.row.validStart }} - {{ scope.row.validEnd }}
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
          <el-input v-model="addForm.url" placeHolder="请输入" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="有效期">
          <el-date-picker
              v-model="addForm.validRange"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd HH:mm:ss"
              @change="addFormValidRangeChange">
          </el-date-picker>
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
          <el-input v-model="editForm.url" placeHolder="请输入" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="editForm.status" filterable placeholder="状态">
            <el-option v-for="(value, key) in statusEnum" :key="key" :label="value" :value="key"/>
          </el-select>
        </el-form-item>
        <el-form-item label="有效期">
          <el-date-picker
              v-model="editForm.validRange"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd HH:mm:ss"
              @change="editFormValidRangeChange">
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
  name: 'Server',
  data() {
    return {
      dataForm: {
        key: "",
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
        url: null,
        validRange: null,
        validStart: null,
        validEnd: null
      },
      editVisible: false,
      editForm: {
        id: null,
        url: null,
        status: null,
        validRange: null,
        validStart: null,
        validEnd: null
      },
      statusEnum: {
        1: '已激活',
        2: '已失效'
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
      this.dataForm.key = "";
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
      this.addForm.url = null
      this.addForm.validRange = null
      this.addForm.validStart = null
      this.addForm.validEnd = null
      this.addVisible = true;
    },
    add() {
      createShorturl(this.addForm)
          .then((response) => {
            if (response.code === CONSTANTS.SUCCESS_CODE) {
              this.search();
              this.addVisible = false
              this.addForm.url = null
              this.addForm.validRange = null
              this.addForm.validStart = null
              this.addForm.validEnd = null
            }
          })
          .catch((err) => {
            console.error(err)
          })
    },
    showEdit(row) {
      this.editForm.id = row.id
      this.editForm.url = row.url
      this.editForm.status = row.status + ''
      this.editForm.validStart = row.validStart
      this.editForm.validEnd = row.validEnd
      this.editForm.validRange = [row.validStart, row.validEnd]
      this.editVisible = true
    },
    edit() {
      updateShorturl(this.editForm)
          .then((response) => {
            if (response.code === CONSTANTS.SUCCESS_CODE) {
              this.search();
              this.editVisible = false
              this.editForm.id = null
              this.editForm.url = null
              this.editForm.status = null
              this.editForm.validRange = null
              this.editForm.validStart = null
              this.editForm.validEnd = null
            }
          })
          .catch((err) => {
            console.error(err)
          })
    },
    addFormValidRangeChange(timeRange) {
      if (timeRange != null && timeRange.length === 2) {
        this.addForm.validStart = timeRange[0]
        this.addForm.validEnd = timeRange[1]
      }
    },
    editFormValidRangeChange(timeRange) {
      if (timeRange != null && timeRange.length === 2) {
        this.editForm.validStart = timeRange[0]
        this.editForm.validEnd = timeRange[1]
      }
    }
  }
}
</script>