
<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-form label-width="120px">
        <el-form-item label="课程标题">
            <el-input v-model="courseInfo.title" placeholder=""/>
        </el-form-item>

        <!-- 所属分类 TODO -->
        <el-form-item label="课程分类">
            <el-select
                v-model="courseInfo.subjectParentId"
                placeholder="一级分类" @change="subjectLevelOneChanged">

                <el-option
                    v-for="subject in subjectOneList"
                    :key="subject.id"
                    :label="subject.title"
                    :value="subject.id"/>

            </el-select>

            <!-- 二级分类 -->
            <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
                <el-option
                    v-for="subject in subjectTwoList"
                    :key="subject.id"
                    :label="subject.title"
                    :value="subject.id"/>
            </el-select>
        </el-form-item>


        <!-- 课程讲师 -->
        <el-form-item label="课程讲师">
        <el-select
            v-model="courseInfo.teacherId"
            placeholder="请选择">
            <el-option
                v-for="teacher in teacherList"
                :key="teacher.id"
                :label="teacher.name"
                :value="teacher.teacherId"/>
        </el-select>
        </el-form-item>

        <el-form-item label="总课时">
            <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
        </el-form-item>

        <!-- 课程简介 TODO -->
        <!-- 课程简介-->
        <el-form-item label="课程简介">
            <tinymce :height="300" v-model="courseInfo.description"/>
        </el-form-item>


        <!-- 课程封面 TODO -->
        <!-- 课程封面-->
        <el-form-item label="课程封面">

            <el-upload
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                :action="BASE_API+'/ossupload/fileoss'"
                class="avatar-uploader">
                <img :src="courseInfo.cover">
            </el-upload>

        </el-form-item>

        <el-form-item label="课程价格">
            <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
        </el-form-item>

        <el-form-item class="routerbox">
            <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
        </el-form-item>
     </el-form>
  </div>
</template>
<script>
import course from '@/api/hscs/course'
import subject from '@/api/hscs/subject'
import Tinymce from '@/components/Tinymce'

export default {
     components: { Tinymce }, //声明组件
    data() {
        return {
            saveBtnDisabled:false,
             courseInfo:{
                title: '',
                teacherId: '',
                subjectId: '',//二级分类id
                subjectParentId:'',//一级分类id
                lessonNum: 0,
                description: '',
                cover: '/static/01.jpg',
                price: 0
            },
            teacherList: [], // 用于封装所有讲师
            subjectOneList: [], //一级分类
            subjectTwoList: [],
            BASE_API: process.env.BASE_API,
            courseId: ''
        }   
    },
    watch: { // vue监听
        $route(to, form) { // 路由变化方式，路由变化就执行方法
        this.courseInfo = {}
        }
    },
    created() {
        //获取路由中的courseid
        if (this.$route.params && this.$route.params.id) {
            this.courseId = this.$route.params.id
            //调用根据id查询课程信息的方法
            this.getCourseInfo()
        }else {
             //初始化所有讲师
             this.getListTeacher()
            //初始化所有一级分类
            this.getOneSubject()
        }
        
    },
    methods:{
        //根据课程id获取课程信息用于回显
        getCourseInfo() {
            course.getCourseInfoId(this.courseId)
                .then( response => {
                    //返回的courseInfoVO没有包含一级分类下对应的二级分类
                    this.courseInfo=response.data.courseInfoVO
                    //查询所有的一级和二级分类
                    subject.getSubjectList() 
                        .then( response => {
                            //获取所有的一级分类
                            this.subjectOneList = response.data.list

                            //遍历所有的一级分类，比较当前courseInfo里面的一级分类id和所有的一级分类id
                            for (var i=0; i<this.subjectOneList.length; i++) {
                                var oneSubject = this.subjectOneList[i]
                                if (this.courseInfo.subjectParentId == oneSubject.id) {
                                    //获取当前一级分类下的所有二级分类
                                    this.subjectTwoList = oneSubject.children
                                }
                            }
                        })
                        //下拉框选中讲师
                         this.getListTeacher()
                })
        },
        //封面上传成功回调
        handleAvatarSuccess (res, file){
            this.courseInfo.cover = res.data.url
        },
        //上传之前调用
        // beforeAvatarUpload (file){
        //     const isJPG = file.type === 'image/ jpg'
        //     const isLt5M =file.size / 1024 /1024 < 5

        //     if(!isJPG) {
        //         this.$message.error('上传头像只能为jpg格式！')
        //     }
        //     if(isLt5M) {
        //         this.$message.error('上传图片大小不能超过5MB！')
        //     }
        //     return isJPG && isLt5M
        // },
        
        //点击一级分类触发change监听，显示对应的二级分类,参数为一级分类的id
        subjectLevelOneChanged(value){
            //遍历一级和二级分类
            for (var i=0; i< this.subjectOneList.length; i++ ) {
                //匹配一级分类id和点击的一级分类id，获取该一级下的二级分类
                if (this.subjectOneList[i].id === value) {
                    this.subjectTwoList = this.subjectOneList[i].children
                    //清空二级分类id
                    this.courseInfo.subjectId = ''
                }
            }
        },
        //查询所有一级分类
        getOneSubject(){
            subject.getSubjectList()
                .then(response => {
                    this.subjectOneList = response.data.list
                })
        },
        //查询所有讲师
        getListTeacher() {
            course.getListTeacher()
                .then(response =>{
                    this.teacherList=response.data.teacherList
                })
        },
        //添加课程
        addCourse() {
             course.addCourseInfo(this.courseInfo)
                .then( response => {
                    this.$message({
                        type: 'success',
                        message: '添加课程信息成功!'
                    })
                     this.$router.push({ path: '/course/chapter/' + response.data.courseId})
                });
        },
        //修改课程信息
        updateCourse() {
            course.updateCourseInfo(this.courseInfo)
                .then(response => {
                     this.$message({
                        type: 'success',
                        message: '修改课程信息成功!'
                    })
                     this.$router.push({ path: '/course/chapter/' + this.courseId})
                })
        },
        saveOrUpdate() {
           //判断是修改还是添加
           if (!this.courseInfo.id){
               this.addCourse()
           }else {
               this.updateCourse()
           }
        
        }
    } 
}
// :scope表示当前页面有效
</script>
<style scoped> 
.routerbox{
    text-align: center;
}
.tinymce-container {
  line-height: 29px;
}
</style>