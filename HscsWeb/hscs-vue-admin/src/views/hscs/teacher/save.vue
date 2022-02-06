<template>
  <div class="app-controller">
    <h2 style="text-align: center;">教师添加</h2>
    <el-form label-width="120px">
      <el-form-item label="教师名称">
        <el-input v-model="teacher.name"/>
      </el-form-item>

      <el-form-item label="教师性别">
        <el-select v-model="teacher.sex" clearable placeholder="请选择">
          <el-option value="女" label="女"/>
          <el-option value="男" label="男"/>
        </el-select>
      </el-form-item>
      
       <el-form-item label="教师编号">
        <el-input v-model="teacher.number"/>
      </el-form-item>
      
        <el-form-item label="教师电话">
        <el-input v-model="teacher.phone"/>
      </el-form-item>

        <el-form-item label="教师邮箱">
        <el-input v-model="teacher.email"/>
      </el-form-item>

      <el-form-item label="教师排序">
        <el-input-number v-model="teacher.sort" controls-position="right" />
      </el-form-item>

      <el-form-item label="教师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <el-option :value="0" label="普通教师"/>
          <el-option :value="1" label="高级教师"/>
          <el-option :value="2" label="特级教师"/>
          <el-option :value="3" label="首席教师"/>
        </el-select>
      </el-form-item>

      <el-form-item label="教师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>

      <el-form-item label="教师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
      </el-form-item>

      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">

          <!-- 头衔缩略图 -->
        <pan-thumb :image="teacher.avatar"/>
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
        </el-button> 

        <!--
        v-show：是否显示上传组件
        :key：类似于id，如果一个页面多个图片上传控件，可以做区分
        :url：后台上传的url地址
        field: 等同于 <input type="file" name="file"/>
        @close：关闭上传组件
        @crop-upload-success：上传成功后的回调
        -->
        <image-cropper
              v-show="imagecropperShow"
              :width="300"
              :height="300"
              :key="imagecropperKey"
              :url="BASE_API+'/ossupload/fileoss'"
              field="file"
              @close="close"
              @crop-upload-success="cropSuccess"/> 
      </el-form-item> 

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>

  </div>

</template>
<script>
import teacherApi from '@/api/hscs/teacherApi'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
  components: {ImageCropper, PanThumb},
  data() {
    return {
      teacher: {
        name: '',
        sex: '',
        number: '',
        email: '',
        sort: 0,
        level: 1,
        career: '',
        intro: '',
        avatar: 'https://home-school-communication.oss-cn-guangzhou.aliyuncs.com/2020/12/24/a5388483d2264c098b6c3ccb61734b00file.png'
      },
      imagecropperShow: false, // 上传弹框的组件是否显示
      imagecropperKey: 0, // 上传组件key值
      BASE_API: process.env.BASE_API, // 获取dev.env.js的地址（端口号）
      saveBtnDisabled: false // 保存按钮是否禁用
    }
  },
  watch: { // vue监听
    $route(to, form) { // 路由变化方式，路由变化就执行方法
      this.init()
    }
  },
  created() {
    this.init()
  },
  methods: {
    close() { // 上传头像关闭方法
      this.imagecropperShow = false
      // 上传组件初始化
      this.imagecropperKey = this.imagecropperKey+1
    },

    cropSuccess(data) { // 上传成功方法
      this.imagecropperShow = false
    // 将头像路径返回值赋给头像
      this.teacher.avatar = data.url
      this.imagecropperKey = this.imagecropperKey+1
    },
    init() {
      // 判断路径中是否有id
      if (this.$route.params && this.$route.params.teacherId) {
      // 从路径获取id
        const teacherId = this.$route.params.teacherId
        this.getInfo(teacherId)
      } else { // 如果是添加记录先清空表单数据
        // console.log('添加记录');
        this.teacher = {}
      }
    },
    getInfo(teacherId) {
      teacherApi.getTeacherById(teacherId)
        .then(response => {
          this.teacher = response.data.teacher;
          // console.log(this.teacher);
        })
    },
    saveOrUpdate() {
      // 根据id判断是添加还是修改
      if (this.teacher.teacherId) {
        this.updateTeacher()
      } else {
        this.saveTeacher()
      }
    },
    saveTeacher() {
      teacherApi.addTeacher(this.teacher)
        .then(response => {
          // 添加成功弹出提示信息
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          // 回到列表页面(路由跳转)
          this.$router.push({ path: '/info/table' })
        })
    },
    updateTeacher() {
      teacherApi.updateTeacher(this.teacher)
        .then(response => {
          this.$message({
            type: 'success',
            message: '更新成功!'
          })
          this.$router.push({ path: '/info/table' })
        })
    }
  }
}
</script>

