<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="逻辑删除标记" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="delFlag">
              <a-input v-model="model.delFlag" placeholder="请输入逻辑删除标记"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="版本号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="code">
              <a-input v-model="model.code" placeholder="请输入版本号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="包名" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="name">
              <a-input v-model="model.name" placeholder="请输入包名"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="下载地址" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="location">
              <a-input v-model="model.location" placeholder="请输入下载地址"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="接单司机电话" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="consignorIphone">
              <a-input v-model="model.consignorIphone" placeholder="请输入接单司机电话"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="中转费用" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="transferMoney">
              <a-input-number v-model="model.transferMoney" placeholder="请输入中转费用" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="是否强制更新 0 否 1是" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sstatus">
              <a-input v-model="model.sstatus" placeholder="请输入是否强制更新 0 否 1是"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="更新说明" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="remark">
              <a-textarea v-model="model.remark" rows="4" placeholder="请输入更新说明" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="是否发布" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="issueIs">
              <a-input v-model="model.issueIs" placeholder="请输入是否发布"  ></a-input>
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
    name: 'WmsAppVersionsForm',
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
        },
        url: {
          add: "/wms/wmsAppVersions/add",
          edit: "/wms/wmsAppVersions/edit",
          queryById: "/wms/wmsAppVersions/queryById"
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