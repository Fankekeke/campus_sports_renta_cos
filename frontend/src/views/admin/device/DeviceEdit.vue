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
          <a-form-item label='器材名称' v-bind="formItemLayout">
            <a-input v-decorator="[
            'name',
            { spaces: [{ required: true, message: '请输入器材名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='器材类型' v-bind="formItemLayout">
            <a-select v-decorator="[
            'typeId',
            { spaces: [{ required: true, message: '请输入器材类型!' }] }
            ]" style="width: 100%">
              <a-select-option v-for="(item, index) in consumableType" :value="item.id" :key="index">{{ item.name }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='型号' v-bind="formItemLayout">
            <a-input v-decorator="[
            'model',
            { spaces: [{ required: true, message: '请输入型号!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='品牌' v-bind="formItemLayout">
            <a-input v-decorator="[
            'brand',
            { spaces: [{ required: true, message: '请输入品牌!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='押金' v-bind="formItemLayout">
            <a-input-number :min="1" style="width: 100%" v-decorator="[
            'depositPrice',
            { spaces: [{ required: true, message: '请输入押金!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='单价/小时' v-bind="formItemLayout">
            <a-input-number :min="1" style="width: 100%" v-decorator="[
            'unitPrice',
            { spaces: [{ required: true, message: '请输入单价/小时!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='器械备注' v-bind="formItemLayout">
            <a-textarea :rows="6" v-decorator="[
            'content',
             { spaces: [{ required: true, message: '请输入器械备注!' }] }
            ]"/>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
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
      consumableType: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  mounted () {
    this.getConsumableType()
  },
  methods: {
    getConsumableType () {
      this.$get('/cos/device-type-info/list').then((r) => {
        this.consumableType = r.data.data
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
      let fields = ['name', 'typeId', 'model', 'brand', 'depositPrice', 'unitPrice', 'content']
      let obj = {}
      Object.keys(space).forEach((key) => {
        if (key === 'images') {
          this.fileList = []
          this.imagesInit(space['images'])
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
      // 获取图片List
      let images = []
      this.fileList.forEach(image => {
        if (image.response !== undefined) {
          images.push(image.response)
        } else {
          images.push(image.name)
        }
      })
      this.form.validateFields((err, values) => {
        values.id = this.rowId
        values.images = images.length > 0 ? images.join(',') : null
        if (!err) {
          this.loading = true
          this.$put('/cos/device-info', {
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
