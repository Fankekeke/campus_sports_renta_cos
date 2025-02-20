<template>
  <a-modal v-model="show" title="采购信息" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px" v-if="rurchaseData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">基础信息</span></a-col>
        <a-col :span="8"><b>采购单号：</b>
          {{ rurchaseData.code }}
        </a-col>
        <a-col :span="8"><b>预计价格：</b>
          {{ rurchaseData.totalPrice }} 元
        </a-col>
        <a-col :span="8"><b>采购人：</b>
          {{ rurchaseData.staffName }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="16"><b>备注信息：</b>
          {{ rurchaseData.content }}
        </a-col>
        <a-col :span="8"><b>采购时间：</b>
          {{ rurchaseData.createDate }}
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" :gutter="15">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">物品详情</span></a-col>
        <a-col :span="24">
          <a-table :columns="columns" :data-source="goodsList">
          </a-table>
        </a-col>
      </a-row>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
moment.locale('zh-cn')
export default {
  name: 'RurchaseView',
  props: {
    rurchaseShow: {
      type: Boolean,
      default: false
    },
    rurchaseData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.rurchaseShow
      },
      set: function () {
      }
    },
    columns () {
      return [{
        title: '器械名称',
        dataIndex: 'name'
      }, {
        title: '型号',
        dataIndex: 'model'
      }, {
        title: '数量',
        dataIndex: 'num'
      }, {
        title: '所属类型',
        dataIndex: 'type'
      }, {
        title: '品牌',
        dataIndex: 'brand'
      }, {
        title: '单价',
        dataIndex: 'unitPrice'
      }]
    }
  },
  data () {
    return {
      loading: false,
      goodsList: [],
      current: 0
    }
  },
  watch: {
    rurchaseShow: function (value) {
      if (value) {
        this.getGoodsByNum(this.rurchaseData.code)
      }
    }
  },
  methods: {
    getGoodsByNum (code) {
      this.$get('/cos/purchase-device-info/queryPurchaseDetail', { code }).then((r) => {
        this.goodsList = r.data.data
      })
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
