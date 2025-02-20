<template>
  <a-drawer
    title="采购入库"
    :maskClosable="false"
    placement="left"
    :closable="false"
    :visible="show"
    :width="1200"
    @close="onClose"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;"
  >
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="12">
          <a-form-item label='采购人' v-bind="formItemLayout">
            <a-select v-decorator="[
              'chargePerson',
              { rules: [{ required: true, message: '请输入采购人!' }] }
              ]">
              <a-select-option :value="item.id" v-for="(item, index) in staffList" :key="index">{{ item.name }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='选择供应商' v-bind="formItemLayout">
            <a-select v-decorator="[
              'supplierId',
              { rules: [{ required: true, message: '请输入供应商!' }] }
              ]">
              <a-select-option :value="item.id" v-for="(item, index) in supplierList" :key="index">{{ item.name }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='备注' v-bind="formItemLayout">
            <a-textarea :rows="4" v-decorator="[
            'content',
             { rules: [{ required: true, message: '请输入备注消息!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-table :columns="columns" :data-source="dataList">
            <template slot="nameShow" slot-scope="text, record">
              <a-input v-model="record.name"></a-input>
            </template>
            <template slot="typeShow" slot-scope="text, record">
              <a-input v-model="record.model"></a-input>
            </template>
            <template slot="typeIdShow" slot-scope="text, record">
              <a-select v-model="record.typeId" style="width: 100%">
                <a-select-option v-for="(item, index) in consumableType" :value="item.id" :key="index">{{ item.name }}</a-select-option>
              </a-select>
            </template>
            <template slot="unitShow" slot-scope="text, record">
              <a-input v-model="record.brand"></a-input>
            </template>
            <template slot="amountShow" slot-scope="text, record">
              <a-input-number v-model="record.num" :min="1" :step="1"/>
            </template>
            <template slot="priceShow" slot-scope="text, record">
              <a-input-number v-model="record.unitPrice" :min="1"/>
            </template>
          </a-table>
          <a-button @click="dataAdd" type="primary" ghost size="large" style="margin-top: 10px;width: 100%">
            新增器械
          </a-button>
        </a-col>
      </a-row>
    </a-form>
    <div class="drawer-bootom-button">
      <a-popconfirm title="确定放弃编辑？" @confirm="onClose" okText="确定" cancelText="取消">
        <a-button style="margin-right: .8rem">取消</a-button>
      </a-popconfirm>
      <a-button @click="handleSubmit" type="primary" :loading="loading">提交</a-button>
    </div>
  </a-drawer>
</template>

<script>
import {mapState} from 'vuex'
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'RurchaseAdd',
  props: {
    rurchaseAddVisiable: {
      default: false
    },
    rurchaseData: {
      type: Object
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.rurchaseAddVisiable
      },
      set: function () {
      }
    },
    columns () {
      return [{
        title: '器械名称',
        dataIndex: 'name',
        scopedSlots: {customRender: 'nameShow'}
      }, {
        title: '型号',
        dataIndex: 'model',
        scopedSlots: {customRender: 'typeShow'}
      }, {
        title: '数量',
        dataIndex: 'num',
        scopedSlots: {customRender: 'amountShow'}
      }, {
        title: '所属类型',
        dataIndex: 'typeId',
        width: 200,
        scopedSlots: {customRender: 'typeIdShow'}
      }, {
        title: '品牌',
        dataIndex: 'brand',
        scopedSlots: {customRender: 'unitShow'}
      }, {
        title: '单价',
        dataIndex: 'unitPrice',
        scopedSlots: {customRender: 'priceShow'}
      }]
    }
  },
  data () {
    return {
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      dataList: [],
      supplierList: [],
      staffList: [],
      consumableType: []
    }
  },
  mounted () {
    this.selectStaffList()
    this.getConsumableType()
    this.selectSupplierList()
  },
  methods: {
    getGoodsByNum (num) {
      this.$get('/cos/goods-belong/getGoodsByNum', { num }).then((r) => {
        this.dataList = r.data.data
      })
    },
    selectStaffList () {
      this.$get(`/cos/staff-info/list`).then((r) => {
        this.staffList = r.data.data
      })
    },
    selectSupplierList () {
      this.$get(`/cos/enterprise-info/list`).then((r) => {
        this.supplierList = r.data.data
      })
    },
    getConsumableType () {
      this.$get('/cos/device-type-info/list').then((r) => {
        this.consumableType = r.data.data
      })
    },
    dataAdd () {
      this.dataList.push({name: '', typeId: '', model: '', num: '', unitPrice: '', brand: ''})
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
      let price = 0
      this.dataList.forEach(item => {
        price += item.unitPrice * item.num
      })
      this.form.validateFields((err, values) => {
        if (!err) {
          values.totalPrice = price
          values.goods = JSON.stringify(this.dataList)
          values.id = this.rurchaseData.id
          this.loading = true
          this.$post('/cos/purchase-device-info ', {
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
