<template>
  <div>
    <div class="item-row">
       <a :href="link.href" target="_blank"> <h4>{{link.name}}</h4> </a> 
        <el-button  type="text" @click="remove"><i class="fa fa-remove"></i></el-button>
     </div>    
      <div class="link-tags">
            <el-tag size="mini">标签一</el-tag>
            <el-tag type="success">标签二</el-tag>
        </div>  
    
    <div class="link-content item-row">
       <div>{{link.href}}</div> 
       <div>{{link.createTime}}</div>
    </div>
    <div class="line"></div>
  </div>
</template>

<script>
import { delLink } from '@/api/api';
    export default {
		data(){
			return {
				key:'',
			}
        },
        methods:{
            remove(){
                 this.$emit('remove',this.link.id);
                
            }
        },
		props:['link']
	}

</script>
<style lang="css">
.item-row{
    display: flex;
   justify-content:  space-between;
}
.link-tags{
     margin:-5px 0 5px;
}
.link-content{
    color: #aaa;
}
.line {
    margin:10px 0;
    content: " ";
    height: 1px;
    border-top: 1px solid #d9d9d9;
    color: #d9d9d9;
    -webkit-transform-origin: 0 0;
    transform-origin: 0 0;
    -webkit-transform: scaleY(0.5);
    transform: scaleY(0.5);
    left: 15px;
  }
</style>
