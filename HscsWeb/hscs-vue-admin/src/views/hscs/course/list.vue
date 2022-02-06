<template>
  <div class="app-container">
    <h2 style="text-align: center;"> 课程列表</h2>
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="courseQuery.title" placeholder="课程名称"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="courseQuery.status" clearable placeholder="课程状态">
          <el-option value="Normal" label="已发布"/>
          <el-option value="Draft" label="未发布"/>
        </el-select>
      </el-form-item>

      <el-form-item>
          <el-date-picker
                v-model="courseQuery.gmtCreate"
                type="date"
                placeholder="选择添加日期"
                format="yyyy-MM -dd "
                value-format="yyyy-MM-dd"
          />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="pageCourseCondition()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row>

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="title" label="课程名称" align="center" />

      <el-table-column label="课程状态" width="100" align="center">
        <template slot-scope="scope">
              <span v-if="scope.row.status==='Normal'" style="color: green">已发布</span>
              <span v-if="scope.row.status==='Draft'">未发布</span>
        </template>
      </el-table-column>

      <el-table-column prop="lessonNum" label="课时数" width="80" align="center" />

      <el-table-column prop="gmtCreate" label="添加时间" width="200" align="center" />

      <el-table-column prop="viewCount" label="浏览数量" width="80" align="center" />

      <el-table-column label="操作" width="440" align="center">
        <template slot-scope="scope">
          <router-link :to="'/course/info/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程基本信息</el-button>
          </router-link>
          <router-link :to="'/course/chapter/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程大纲信息</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除课程信息</el-button>
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
    />

  </div>
</template>
<script>
//引入调用teacher.js文件
import course from '@/api/hscs/course'

export default {
    //写核心代码位置
    // data:{
    // },
    data() { //定义变量和初始值
        return {
          list:null,//查询之后接口返回集合
          page:1,//当前页
          limit:10,//每页记录数
          total:0,//总记录数
          courseQuery:{} //条件封装对象
        }
    },
    created() { 
        //查询表单数据
        this.getList() 
    },
    methods:{  
        //删除课程
        removeDataById(id){
             this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                course.deleteCourse(id) // 删除方法
                    .then(response => {
                    // 刷新列表
                        this.getList()
                    })
                    this.$message({
                    type: 'success',
                    message: '删除成功!'
                    })
            })
        },

        //课程列表的方法
        getList(page = 1) {
            this.page = page // 点击第二页时传入2
            course.getListCourse(this.page, this.limit)
                .then(response =>{//请求成功
                    //response接口返回的数据
                    this.list = response.data.list
                    this.total = response.data.total
                }) 
        },
        pageCourseCondition(page = 1) { // 条件查询
            this.page = page
            course.getPageCourseCondition(this.page, this.limit, this.courseQuery)
                .then(response => {
                this.list = response.data.courseList
                this.total = response.data.total
                })
        },
        resetData() {//清空的方法
            //表单输入项数据清空
            this.courseQuery = {}
            //查询所有讲师数据
            this.getList()
        }
 
    }
}
</script>
