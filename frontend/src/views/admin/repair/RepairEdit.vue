<template>
  <a-modal v-model="show" title="修改器材" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        修改
      </a-button>
    </template>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="12">
          <a-form-item label='选择器材' v-bind="formItemLayout">
            <a-select v-decorator="[
              'deviceId',
              { rules: [{ required: true, message: '请选择器材!' }] }
              ]">
              <a-select-option :value="item.id" v-for="(item, index) in deviceList" :key="index">{{ item.name }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='维修人员' v-bind="formItemLayout">
            <a-select v-decorator="[
              'staffId',
              { rules: [{ required: true, message: '请选择维修人员!' }] }
              ]">
              <a-select-option :value="item.id" v-for="(item, index) in staffList" :key="index">{{ item.name }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='维修主题' v-bind="formItemLayout">
            <a-input v-decorator="[
            'title',
            { spaces: [{ required: true, message: '请输入维修主题!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='开始维修时间' v-bind="formItemLayout">
            <a-date-picker show-time
                           format="YYYY-MM-DD HH:mm:ss" style="width: 100%" v-decorator="[
            'repairStartDate',
            { rules: [{ required: true, message: '请输入开始维修时间!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='维修结束时间' v-bind="formItemLayout">
            <a-date-picker show-time
                           format="YYYY-MM-DD HH:mm:ss" style="width: 100%" v-decorator="[
            'repairEndDate',
            { rules: [{ required: true, message: '请输入维修结束时间!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='维修内容' v-bind="formItemLayout">
            <a-textarea :rows="6" v-decorator="[
            'content',
             { spaces: [{ required: true, message: '请输入维修内容!' }] }
            ]"/>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
import moment from 'moment'
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'spaceEdit',
  props: {
    spaceEditVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.spaceEditVisiable
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      rowId: null,
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      deviceList: [],
      staffList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  mounted () {
    this.selectDeviceList()
    this.selectStaffList()
  },
  methods: {
    selectDeviceList () {
      this.$get(`/cos/device-info/list`).then((r) => {
        this.deviceList = r.data.data
      })
    },
    selectStaffList () {
      this.$get(`/cos/staff-info/list`).then((r) => {
        this.staffList = r.data.data
      })
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    setFormValues ({...space}) {
      this.rowId = space.id
      let fields = ['title', 'deviceId', 'content', 'repairStartDate', 'repairEndDate', 'staffId']
      let obj = {}
      Object.keys(space).forEach((key) => {
        if (key === 'repairStartDate' && space[key] != null) {
          space[key] = moment(space[key])
        }
        if (key === 'repairEndDate' && space[key] != null) {
          space[key] = moment(space[key])
        }
        if (key === 'type' && space[key] != null) {
          space[key] = space[key].toString()
        }
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = space[key]
        }
      })
      this.form.setFieldsValue(obj)
    },
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        values.id = this.rowId
        if (values.repairStartDate) {
          values.repairStartDate = moment(values.repairStartDate).format('YYYY-MM-DD HH:mm:ss')
        }
        if (values.repairEndDate) {
          values.repairEndDate = moment(values.repairEndDate).format('YYYY-MM-DD HH:mm:ss')
        }
        if (!err) {
          this.loading = true
          this.$put('/cos/repair-record-info', {
            ...values
          }).then((r) => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
