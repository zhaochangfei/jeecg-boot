<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    switchFullscreen
    :okButtonProps="{ class:{'jee-hidden': disableSubmit} }"
    @cancel="handleCancel"
    cancelText="关闭">
    <wms-trans-order-form ref="realForm" :disabled="disableSubmit"></wms-trans-order-form>
  </j-modal>
</template>

<script>
import WmsTransOrderForm from '@views/modules/wms/modules/WmsTransOrderForm.vue'

export default {
  name: 'WmsTransOrderModal',
  components: { WmsTransOrderForm },
  data () {
    return {
      title:'打印',
      width:1500,
      visible: false,
      disableSubmit: true
    }
  },
  methods: {
    add () {
      this.visible=true
      this.$nextTick(()=>{
        this.$refs.realForm.add();
      })
    },
    print (record) {
      this.visible=true
      this.$nextTick(()=>{
        this.$refs.realForm.edit(record);
      })
    },
    close () {
      this.$emit('close');
      this.visible = false;
    },
    handleCancel () {
      this.close()
    }
  }
}
</script>

<style scoped>

</style>