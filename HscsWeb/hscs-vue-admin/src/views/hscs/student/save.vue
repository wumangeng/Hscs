<template>
  <div class="app-controller">
    <h2 style="text-align: center;">添加学生信息</h2>
    
    <el-form label-width="180px" :rules="rules" > 
      <el-row>
        <el-col span="10">
          <el-form-item label="学生姓名" prop="name" > 
            <el-input v-model="student.name"  />
          </el-form-item>
        </el-col>
        <el-col span="12">
        <el-form-item  label="学生性别" >
          <el-select v-model="student.gender" clearable placeholder="请选择">
            <el-option value="女" label="女"/>
            <el-option value="男" label="男"/>
          </el-select>
        </el-form-item>
        </el-col>
      </el-row>
      
      <el-row>
        <el-col span="10">
          <el-form-item label="学生学号">
            <el-input v-model="student.studentNumber"/>
          </el-form-item>
        </el-col>
        <el-col span="10">
          <el-form-item label="班级名称">
            <el-input v-model="student.className"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col span="10">
          <el-form-item label="班主任">
            <el-input v-model="student.teacherName"/>
          </el-form-item>
        </el-col>
        <el-col span="10">
          <el-form-item label="班主任电话">
            <el-input v-model="student.teacherPhone" prop="phone" />
          </el-form-item>
        </el-col>
      </el-row>

    <el-row>
        <el-col span="10">
          <el-form-item label="出生日期" >
            <el-date-picker
                  v-model="student.birthday"
                  type="date"
                  placeholder="出生日期"
                  value-format="yyyy-MM-dd"
            />
          </el-form-item>
        </el-col>
        <el-col span="10">
          <el-form-item label="学生身份证号">
            <el-input v-model="student.idcard"/>
          </el-form-item>
        </el-col>
    </el-row>

    <el-row>
      <el-col span="10">
        <el-form-item label="家长姓名">
          <el-input v-model="student.parentName"/>
        </el-form-item>
      </el-col>
      <el-col span="10">
        <el-form-item label="家长电话" prop="phone">
          <el-input v-model="student.phone"/>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col span="10">
        <el-form-item label="家长身份证号">
          <el-input v-model="student.parentIdcard"/>
        </el-form-item>
      </el-col>
      <el-col span="10">
        <el-form-item label="民族">
          <el-input v-model="student.nation"/>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col span="10">
        <el-form-item label="籍贯">
          <el-input v-model="student.nativePlace"/>
        </el-form-item>
      </el-col>
      <el-col span="10">
        <el-form-item label="政治面貌">
          <el-input v-model="student.politicId"/>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col span="10">
        <el-form-item label="家庭地址">
          <el-input v-model="student.address"/>
        </el-form-item>
       </el-col>
      <el-col span="10">
        <el-form-item label="入学日期" >
              <el-date-picker
                    v-model="student.beginDate"
                    type="date"
                    placeholder="入学日期"
                    value-format="yyyy-MM-dd"
              />
        </el-form-item>
     </el-col>
    </el-row>

     <el-form-item class="box">
      <br/>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate" style="width:120px;">保存</el-button>
     </el-form-item>
    </el-form>

  </div>

</template>
<script>
import student from '@/api/hscs/student'

export default {
  data() {
    return {
      student: {
        name: '',
        gender: '',
        studentNumber: '',
        className: '',
        teacherName: '',
        teacherPhone: '',
        birthday: '',
        idcard: '',
        parentName: '',
        phone: '',
        parentIdcard: '',
        nation: '',
        nativePlace: '',
        politicId: '',
        address: ''
      },
      rules:{
	      	name :[{required: true, message: '请输入学生姓名', trigger: 'blur'}],
          phone :[ { min: 0, max: 11, message: '请输入正确的电话号码', trigger: 'blur' }]
	    }
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
    init() {
      // 判断路径中是否有id
      if (this.$route.params && this.$route.params.id) {
      // 从路径获取id
        const id = this.$route.params.id
        this.getInfo(id)
      } else {
      // 如果是添加记录先清空表单数据
        this.student = {}
      }
    },
    getInfo(id) {
      student.getStudentById(id)
        .then(response => {
          this.student = response.data.student;
          // console.log(this.student);
        })
    },
    saveOrUpdate() {
      // 根据id判断是添加还是修改
      if (this.student.id) {
        this.updateStudent()
      } else {
        this.saveStudent()
      }
    },
    saveStudent() {
      student.addStudent(this.student)
        .then(response => {
          // 添加成功弹出提示信息
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          // 回到列表页面(路由跳转)
          this.$router.push({ path: '/info/student/list' })
        })
    },
    updateStudent() {
      student.updateStudent(this.student)
        .then(response => {
          this.$message({
            type: 'success',
            message: '更新成功!'
          })
          this.$router.push({ path: '/info/student/list' })
        })
    }
  }
}
</script>

<style scoped>
.box{
  text-align: center;
  width: 80%;
}
</style>

