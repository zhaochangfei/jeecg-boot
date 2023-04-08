<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="12">
            <a-form-model-item label="名字" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="name">
              <a-input v-model="model.name" placeholder="请输入名字"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="手机号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="iphone">
              <a-input v-model="model.iphone" placeholder="请输入手机号"  ></a-input>
            </a-form-model-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-model-item label="身份证号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="idcard">
              <a-input v-model="model.idcard" placeholder="请输入身份证号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="详细地址" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="address">
              <a-input v-model="model.address" placeholder="请输入详细地址"  ></a-input>
            </a-form-model-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-model-item label="打卡" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="clockIn">
              <j-dict-select-tag type="list" v-model="model.clockIn" dictCode="is_zc" placeholder="请选择是否支持打卡" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="月结" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="monthlyStatement">
              <j-dict-select-tag type="list" v-model="model.monthlyStatement" dictCode="is_zc" placeholder="请选择是否支持月结" />
            </a-form-model-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-model-item label="回单付" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="hdf">
              <j-dict-select-tag type="list" v-model="model.hdf" dictCode="is_zc" placeholder="请选择是否支持回单付" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="城市" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="city">
              <a-input v-model="model.city" placeholder="请输入城市"  ></a-input>
            </a-form-model-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-model-item label="银行" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="bank">
              <a-input v-model="model.bank" placeholder="请输入银行"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="银行卡" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="bankCard">
              <a-input v-model="model.bankCard" placeholder="请输入银行卡"  ></a-input>
            </a-form-model-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="remark">
              <a-input type="textarea" :rows="3" v-model="model.remark" placeholder="请输入备注"  ></a-input>
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

  export default {
    name: 'WmsConsignorForm',
    components: {
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        model:{
         },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          name: [{ required: true, message: '请输入名字!' }],
          iphone: [{ required: true, message: '请输入手机号!' }],
        },
        url: {
          add: "/wms/wmsConsignor/add",
          edit: "/wms/wmsConsignor/edit",
          queryById: "/wms/wmsConsignor/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
       //备份model原始值
      this.modelDefault = JSON.parse(JSON.stringify(this.model));
    },
    methods: {
      add () {
        this.edit(this.modelDefault);
      },
      edit (record) {
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.$refs.form.validate(valid => {
          if (valid) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            httpAction(httpurl,this.model,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
    }
  }
</script>