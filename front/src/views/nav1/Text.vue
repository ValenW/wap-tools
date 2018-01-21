<template lang="html">
  <section>
    <el-col :span="14">
    <p> Please input you text resource. The order is textid,en,ja. The title line should not be included.</p>
      <el-input
        type="textarea"
        :rows="6"
        placeholder="please input you text resource. "
        v-model="text">
      </el-input>
     <el-button type="primary" @click="generate" style="margin-top:10px" :disabled="!clickable">Generate</el-button>
     </el-col>
</section>
</template>

<script>
import util from '@/common/js/util.js'
import { saveTextapi } from '../../api/api'

export default {
  data() {
    return {
      text: ''
    }
  },
  computed: {
    clickable() {
      return this.text && this.text !== ''
    }
  },
  methods: {
    convert() {
      console.log(this.text.split('\n'))
      if (!this.text || this.text === '') {
        return
      }
      var lines = this.text.split('\n')
      var list = []
      lines.forEach(item => {
        if (item.startsWith('"')) {
          item = item.replace(/","/g, ',')
          item = item.slice(1, -1)
        }
        var arr = item.split(',')
        list.push({
          id: arr[0],
          en: arr[1],
          ja: arr[2]
        })
      })
      return list
    },
    save(list) {
      saveTextapi(list).then(res => {
        console.log(res)
      })
    },
    generate() {
      const list = this.convert()
      this.save(list)
      var str = util.genText(list)
      util.download(str, util.getFileName())
    }

  }
}
</script>

<style lang="css">
</style>
