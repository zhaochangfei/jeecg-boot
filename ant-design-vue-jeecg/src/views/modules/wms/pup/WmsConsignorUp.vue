<template>
  <!-- 选择发货人员-->
  <div>
    <a-modal
      :maskClosable="false"
      centered
      :title="title"
      :width="1180"
      :visible="visible"
      @cancel="handleCancel"
      cancelText="关闭"
    >
      <a-row :gutter="10">
        <a-col :span="24">
          <a-card :bordered="false">
            <div class="table-page-search-wrapper">
              <a-form layout="inline" @keyup.enter.native="searchQuery">
                <a-row :gutter="24">
                  <a-col :span="5">
                    <a-form-item label="发货人名称">
                      <j-input placeholder="请输入发货人名称" v-model="queryParam.name"></j-input>
                    </a-form-item>
                  </a-col>
                  <a-col :span="5">
                    <a-form-item label="发货人电话">
                      <j-input placeholder="请输入发货人电话" v-model="queryParam.iphone"></j-input>
                    </a-form-item>
                  </a-col>
 
                  <a-col :span="4">
                    <span style="float: left; margin-top: -3px" class="table-page-search-submitButtons">
                      <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
                      <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px"
                        >重置</a-button
                      >
                    </span>
                  </a-col>
                </a-row>
              </a-form>
            </div>
            <!-- table区域-begin -->
            <div>
              <a-table
                size="small"
                rowKey="id"
                :columns="columns"
                :dataSource="dataSource"
                :pagination="ipagination"
                :loading="loading"
                :scroll="{ y: 240 }"
                :rowSelection="{
                  selectedRowKeys: selectedRowKeys,
                  onSelectAll: onSelectAll,
                  onSelect: onSelect,
                  onChange: onSelectChange,
                  type: 'radio',
                }"
                @change="handleTableChange"
                :customRow="clickThenCheck"
              >
              </a-table>
            </div>
          </a-card>
        </a-col>
      </a-row>
      <template slot="footer">
        <a-button @click="handleCancel"> 关闭 </a-button>
        <a-button key="submit" type="primary" :loading="loading" @click="handleOk"> 确定</a-button>
      </template>
    </a-modal>
  </div>
</template>

<script>
import { getAction } from '@/api/manage'
import { filterObj } from '@/utils/util'

export default {
  // import引入的组件
  components: {},
  name: 'wmsConsignorUp',
  data() {
    let ellipsis = (v, l = 20) => <j-ellipsis value={v} length={l} />
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      title: '选择发货人员',
      visible: false,
      model: {
        cpInStoreOrder: {},
      },
      queryParam: {},
      // 表头
      columns: [
        {
          title: '名字',
          align: 'center',
          width: 100,
          dataIndex: 'name',
        },
        {
          title: '手机号',
          align: 'center',
          dataIndex: 'iphone',
        },

        {
          title: '身份证号',
          align: 'center',
          dataIndex: 'idcard',
        },
        {
          title: '地址',
          align: 'center',
          dataIndex: 'address',
        },
      ],
      // 数据集
      dataSource: [],
      dataSource2: [],
      // 分页参数
      ipagination: {
        current: 1,
        pageSize: 10,
        pageSizeOptions: ['10', '20', '30'],
        showTotal: (total, range) => {
          return range[0] + '-' + range[1] + ' 共' + total + '条'
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0,
      },
      isorter: {
        column: 'createTime',
        order: 'desc',
      },
      loading: false,
      selectedRowKeys: [],
      selectedRows: [],
      selectionRows: [],
      productionId: '',
      validatorRules: {},
      pcStoreList: [],
      key: '',
      url: {
        list: '/wms/wmsConsignee/list',
      },
      craftOrderTree: [],
      selectedKeys: [],
      autoExpandParent: true,
      iExpandedKeys: [],
      checkedCraftOrder: {},

      position: '',
      status: '',
    }
  },
  created() {},
  // 方法集合
  methods: {
    searchQuery() {
      this.loadData(1)
    },
    loadData(arg) {
      // 加载数据 若传入参数1则加载第一页的内容
      if (arg === 1) {
        this.ipagination.current = 1
      }
      var params = this.getQueryParams() // 查询条件
      getAction(this.url.list, params).then((res) => {
        if (res.success) {
          this.dataSource = res.result.records
          this.ipagination.total = res.result.total
        }
      })
    },
    getQueryParams() {
      var param = Object.assign({}, this.queryParam, this.isorter)
      param.position = this.position
      param.status = this.status

      param.field = this.getQueryField()
      param.pageNo = this.ipagination.current
      param.pageSize = this.ipagination.pageSize
      return filterObj(param)
    },
    getQueryField() {
      // TODO 字段权限控制
    },
    onSelectAll(selected, selectedRows, changeRows) {
      if (selected === true) {
        for (var a = 0; a < changeRows.length; a++) {
          this.dataSource2.push(changeRows[a])
        }
      } else {
        for (var b = 0; b < changeRows.length; b++) {
          this.dataSource2.splice(this.dataSource2.indexOf(changeRows[b]), 1)
        }
      }
    },
    handleTableChange(pagination, filters, sorter) {
      if (Object.keys(sorter).length > 0) {
        this.isorter.column = sorter.field
        this.isorter.order = sorter.order == 'ascend' ? 'asc' : 'desc'
      }
      this.ipagination = pagination
      this.loadData()
    },
    searchReset() {
      this.queryParam = {}
      this.loadData(1)
    },
    onSelect(record, selected) {
      if (selected === true) {
        this.dataSource2.push(record)
      } else {
        var index = this.dataSource2.indexOf(record)
        if (index >= 0) {
          this.dataSource2.splice(this.dataSource2.indexOf(record), 1)
        }
      }
    },
    onSelectChange(selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectionRows = selectedRows
    },
    handleShow() {},
    handleOk() {
      if (this.selectionRows.length > 0) {
        this.selectionRows[0].key = this.key
        this.$emit('wmsConsignorUp', this.selectionRows)
        this.visible = false
      } else {
        this.$message.warning('请勾选')
      }
    },
    handleCancel() {
      this.close()
    },
    initpany() {
      this.visible = true
      this.loadData()
    },
    close() {
      this.$emit('close')
      this.visible = false
    },
    clickThenCheck(record) {
      return {
        on: {
          dblclick: (event) => {
            const keys = []
            const items = []
            keys.push(record.id)
            items.push(record)
            record.key = this.key
            this.onSelectChange(keys, items)
            this.$nextTick(() => {
              this.handleOk()
            })
          },
        },
      }
    },
  },
  // 生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {},
}
</script>
<style lang="less" scoped></style>
