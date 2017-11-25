<template>
	<section>

		 <el-input placeholder="请输入内容" v-model="key" class="link-search" @change="filterLink">			
			<!-- <el-button slot="append" icon="el-icon-search"></el-button> -->
			<el-button slot="append" type="primary" @click="showAdd"><i class="fa fa-plus"></i></el-button>
		</el-input>
		<template v-for="item in links">
			<link-item :link="item" @remove="removeLink"></link-item>
		</template>

		<el-dialog
			title="Add a new link"
			:visible.sync="dialogVisible"
			width="30%"
			>
			
			<el-form ref="form" :model="form" label-width="80px">
				<el-form-item label="name">
					<el-input v-model="form.name"></el-input>
				</el-form-item>
				
				<el-form-item label="href">
					 <el-input type="textarea" v-model="form.href"></el-input>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="dialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="add">确 定</el-button>
			</span>
		</el-dialog>

	</section>
</template>

<script>
import { getLinkList,addLink,delLink } from '../../api/api';
import linkItem from '@/components/linkItem'
	export default {
		data(){
			return {
				dialogVisible:false,
				key:'',
				all:[],
				links:[],
				form:{
					name:'',
					href:''
				}
			}
		},
		components:{
			linkItem,
		},
		methods:{
			filterLink(value){
				this.links=this.all.filter(it=>it.name.indexOf(value)>-1);
				// console.log(value,this.links)
			},
			getLinks(){
				getLinkList().then(res=>{
					console.log(res)
					this.links=res.data;
					this.all=res.data;
				})
			},
			showAdd(){
				this.dialogVisible=true;
			},
			add(){
				addLink(this.form)
				.then(res=>{
					console.log(res)
					this.dialogVisible=false;
					this.getLinks();
				})
			},
			removeLink(id){
				var _this=this;
				 this.$confirm('Are you sure to delete this item?', 'Confirm', {
                    confirmButtonText: 'OK',
                    cancelButtonText: 'Cancel',
                    type: 'warning'
                    }).then(() => {
                        delLink(id).then(res=>{
							console.log(res)
					_this.getLinks();
					_this.$message({
						type: 'success',
						message: 'Deleted!'
							});
                        })                     
                       
                    }).catch(() => {
						this.$message({
							type: 'info',
							message: 'Cancel'
						});          
                    });
			}
		},
		created(){
			this.getLinks();
		}

	}

</script>

<style scoped>
.link-search{
	margin: 10px 0;
}
</style>