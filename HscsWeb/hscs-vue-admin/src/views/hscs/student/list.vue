<template>
  <div id="app-controller">
    <h2 style="text-align: center;">学生信息列表</h2>
   <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="studentQuery.name" placeholder="学生姓名"/>
        </el-form-item>

        <el-form-item>
            <el-input v-model="studentQuery.studentNumber" placeholder="学号"/>
        </el-form-item>

        <el-form-item>
            <el-input v-model="studentQuery.className" placeholder="班级名称"/>
        </el-form-item>

        <el-form-item>
            <el-input v-model="studentQuery.teacherName" placeholder="班主任名字"/>
        </el-form-item>

        <el-form-item>
            <el-date-picker
              v-model="studentQuery.beginDate"
              type="date"
              placeholder="入学日期"
              value-format="yyyy-MM-dd"
            />
        </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="pageStudentCondition()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form-item>
  </el-form>
    <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row>

      <el-table-column
        label="序号"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="name" label="学生姓名" width="100"  align="center" />
      <el-table-column prop="gender" label="性别" width="50"  align="center" />
      <el-table-column prop="studentNumber" label="学号" width="120" :show-overflow-tooltip="true"  align="center" />
      <el-table-column prop="className" label="班级名称" width="100"   align="center" />
      <el-table-column prop="teacherName" label="班主任名字" width="100"   align="center" />
      <el-table-column prop="teacherPhone" label="班主任电话" width="120"   align="center" />
      <el-table-column prop="birthday" label="出生日期"  width="100"   align="center"  />
      <el-table-column prop="idcard" label="学生身份证号" width="120" :show-overflow-tooltip="true" align="center"  />
      <el-table-column prop="parentName" label="家长姓名" width="100"   align="center" />
      <el-table-column prop="phone" label="家长电话" width="120"   align="center" />
      <el-table-column prop="parentIdcard" label="家长身份证号" width="120" :show-overflow-tooltip="true"  align="center"  />
      <el-table-column prop="nation" label="民族" width="50"   align="center" />
      <el-table-column prop="nativePlace" label="籍贯" width="150" :show-overflow-tooltip="true"   align="center" />
      <el-table-column prop="politicId" label="政治面貌" width="60"   align="center" />
      <el-table-column prop="address" label="家庭地址" width="150" :show-overflow-tooltip="true"  align="center" />
      <el-table-column prop="beginDate" label="入学日期" width="100"   align="center" />
      <el-table-column prop="gmtCreate" label="创建时间" width="100" :show-overflow-tooltip="true"  align="center" />
      <el-table-column prop="gmtModified" label="更新时间" width="100" :show-overflow-tooltip="true"  align="center" />

      <el-table-column label="操作" width="200"    align="center" >
        <template slot-scope="scope">
          <router-link :to="'/info/editStudent/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    >
    </el-pagination>
  </div>
</template>

<script>
import student from '@/api/hscs/student'

export default {
    data() {
      return {
           list: null, // 查询结果返回的集合
           page: 1,
           limit: 10,
           total: 0,
           studentQuery: {},// 条件封装对象
      }
    },
    watch: {  // 监听路由变化
      $route(to, form){
        this.init()
      }
    },
    created() {
      this.getList();
    },
    methods: {
      init() {
         // 判断路径中是否有id
        if (this.$route.params && this.$route.params.id) {
         // console.log('出发了');
        // 从路径获取id
          const id = this.$route.params.id
          this.getInfo(id)
        } else { // 如果是添加记录先清空表单数据
          this.student = {}
        }
      },
      getList(page = 1) { // 学生列表的方法
        this.page = page // 点击第二页时传入2
        student.getStudentList(this.page, this.limit)
          .then(response => { // 请求成功返回数据
            this.list = response.data.studentList
            this.total = response.data.total
             console.log("分页查询结果返回：",response)
            // console.log(this.list)
          })
    },
    pageStudentCondition(page = 1) { // 条件查询
      this.page = page
      console.log(this.studentQuery);
      student.getStudentListPage(this.page, this.limit, this.studentQuery)
          .then(response => {
            this.list = response.data.studentList
            this.total = response.data.total
            // console.log(response)
          // console.log('条件查询返回：' + this.list)
          })
    },
    resetData() { // 清空表单方法
      // 利用双向绑定
      this.studentQuery = {}
      // 查询所有
      this.getList()
    },
    removeDataById(id) { // 删除学生信息
      this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        student.deleteStudent(id) // 删除方法
          .then(response => {
          // 刷新列表
            this.getList()
          })
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
      })
    }

    }
}
</script>