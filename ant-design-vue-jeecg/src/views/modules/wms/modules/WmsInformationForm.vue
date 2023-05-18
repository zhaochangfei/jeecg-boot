<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="真实姓名" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="realname">
              <a-input v-model="model.realname" placeholder="请输入真实姓名"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="电话" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="iphone">
              <a-input v-model="model.iphone" placeholder="请输入电话"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="货物" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="goods">
              <a-input v-model="model.goods" placeholder="请输入货物"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="重量" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weight">
              <a-input type="number"  v-model="model.weight" placeholder="请输入重量"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="起运站" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="originatingStation">
              <a-input  v-model="model.originatingStation" placeholder="请输入起运站"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="到达站" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="arrivalStation">
              <a-input v-model="model.arrivalStation" placeholder="请输入到达站"></a-input>
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="24">
            <a-form-model-item label="是否受理" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sstatus">
              <j-dict-select-tag
                type="list"
                v-model="model.sstatus"
                dictCode="information_sstatus"
                placeholder="请选择是否受理"
              />
            </a-form-model-item>
          </a-col> -->
          <a-col :span="24">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="remark">
              <a-textarea v-model="model.remark" rows="4" placeholder="请输入备注" />
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>
import { httpAction, getAction } from '@/api/manage'
import { validateDuplicateValue } from '@/utils/util'
import JDictSelectTag from '../../../../components/dict/JDictSelectTag.vue'

export default {
  name: 'WmsInformationForm',
  components: {
    JDictSelectTag,
  },
  props: {
    //表单禁用
    disabled: {
      type: Boolean,
      default: false,
      required: false,
    },
  },
  data() {
    return {
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      confirmLoading: false,
      validatorRules: {},
      url: {
        add: '/wms/wmsInformation/add',
        edit: '/wms/wmsInformation/edit',
        queryById: '/wms/wmsInformation/queryById',
      },
    }
  },
  computed: {
    formDisabled() {
      return this.disabled
    },
  },
  created() {
    //备份model原始值
    this.modelDefault = JSON.parse(JSON.stringify(this.model))
  },
  methods: {
    add() {
      this.edit(this.modelDefault)
    },
    edit(record) {
      this.model = Object.assign({}, record)
      this.visible = true
    },
    submitForm() {
      const that = this
      // 触发表单验证
      this.$refs.form.validate((valid) => {
        if (valid) {
          that.confirmLoading = true
          let httpurl = ''
          let method = ''
          if (!this.model.id) {
            httpurl += this.url.add
            method = 'post'
          } else {
            httpurl += this.url.edit
            method = 'put'
          }
          httpAction(httpurl, this.model, method)
            .then((res) => {
              if (res.success) {
                that.$message.success(res.message)
                that.$emit('ok')
              } else {
                that.$message.warning(res.message)
              }
            })
            .finally(() => {
              that.confirmLoading = false
            })
        }
      })
    },
  },
}
</script>