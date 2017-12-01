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

var funDownload = function(content, filename) {
  var eleLink = document.createElement('a');
  eleLink.download = filename;
  eleLink.style.display = 'none';
  // 字符内容转变成blob地址
  var blob = new Blob([content]);
  eleLink.href = URL.createObjectURL(blob);
  // 触发点击
  document.body.appendChild(eleLink);
  eleLink.click();
  // 然后移除
  document.body.removeChild(eleLink);
};

export default {
  data() {
    return {
      text: '',
    }
  },
  computed: {
    clickable() {
      return this.text && this.text != ''
    }
  },
  methods: {
    generate() {
      console.log(this.text.split('\n'));
      if (!this.text || this.text == '') {
        return;
      }
      var lines = this.text.split('\n')
      var list = [];
      lines.forEach(item => {
        var arr = item.split(',')
        list.push({
          textId: arr[0],
          en: arr[1],
          ja: arr[2],
        })
      })
      var str = '['
      // console.log(list)
      list.forEach(item => {
        if (item.ja && item.ja != '') {
          str = str.concat(`{"class":"com.worksap.company.hue.core.dto.TextDefDto","pl":"{\\"key\\":{\\"class\\":\\"com.worksap.company.framework.textresource.TextResourceKey\\",\\"pl\\":\\"{\\\\\\"textId\\\\\\":\\\\\\"${item.textId}\\\\\\",\\\\\\"locale\\\\\\":\\\\\\"ja\\\\\\",\\\\\\"plural\\\\\\":\\\\\\"\\\\\\"}\\"},\\"value\\":\\"${item.ja}\\",\\"systemFlg\\":false}"},`)
        }
        if (item.en && item.en != '') {
          str = str.concat(`{"class":"com.worksap.company.hue.core.dto.TextDefDto","pl":"{\\"key\\":{\\"class\\":\\"com.worksap.company.framework.textresource.TextResourceKey\\",\\"pl\\":\\"{\\\\\\"textId\\\\\\":\\\\\\"${item.textId}\\\\\\",\\\\\\"locale\\\\\\":\\\\\\"en\\\\\\",\\\\\\"plural\\\\\\":\\\\\\"\\\\\\"}\\"},\\"value\\":\\"${item.en}\\",\\"systemFlg\\":false}"},`)
        }
      })
      str = str.slice(0, -1);
      str = str.concat(']')
      var name = util.formatDate.format(new Date(), 'yyyyMMddhhmmss') + "_INSERT_com.worksap.company.hue.core.dto.TextDefDto.json";
      funDownload(str, name);
      //  funDownload('hello world', 'sfdf.json')
    }
  }
}
</script>

<style lang="css">
</style>
