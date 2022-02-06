<template>
  <div id="app-controller">
    <h2 style="text-align: center;">教师信息列表</h2>
   <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label = "教师名称：">
        <el-form-item>
          <el-input v-model="teacherQuery.name" placeholder="教师名称"/>
        </el-form-item>

        <el-form-item>
          <el-select v-model="teacherQuery.level" clearable placeholder="教师头衔">
            <el-option :value="0" label="普通教师"/>
            <el-option :value="1" label="高级教师"/>
            <el-option :value="2" label="特级教师"/>
            <el-option :value="3" label="首席教师"/>
          </el-select>
        </el-form-item>

        <el-form-item label="添加时间">
          <el-date-picker
            v-model="teacherQuery.begin"
            type="datetime"
            placeholder="选择开始时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time="00:00:00"
          />
        </el-form-item>
        <el-form-item>
          <el-date-picker
            v-model="teacherQuery.end"
            type="datetime"
            placeholder="选择截止时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time="00:00:00"
          />
        </el-form-item>

        <el-button type="primary" icon="el-icon-search" @click="pageTeacherCondition()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form-item>
    </el-form>

    <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row>

 <!-- width="70" -->
      <el-table-column
        label="序号"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="name" label="名称"  align="center" />

      <el-table-column prop="sex" label="性别"  align="center" />

      <el-table-column prop="number" label="编号"  width="120" align="center"  />

      <el-table-column prop="phone" label="电话"  width="150"  align="center"  />

      <el-table-column prop="email" label="邮箱" width="180" align="center"  />

      <el-table-column label="头衔"  align="center" >
        <template slot-scope="scope" >
          <div v-if="scope.row.level === 0">普通教师</div>
          <div v-else-if ="scope.row.level === 1">高级教师</div>
          <div v-else-if ="scope.row.level === 2" >特级教师</div>
          <div v-else>首席教师</div>
        </template>
      
      </el-table-column>

      <el-table-column prop="career" label="资历" width="180" show-overflow-tooltip  align="center" />

       <el-table-column prop="intro" label="简介" width="200" :show-overflow-tooltip="true"  align="center"  />

      <el-table-column prop="gmtCreate" label="添加时间" width="200"  align="center" />

      <!-- <el-table-column prop="sort" label="排序"  align="center"  /> -->

      <el-table-column label="操作"  align="center" width="200">
        <template slot-scope="scope">
          <router-link :to="'/info/edit/'+scope.row.teacherId">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.teacherId)">删除</el-button>
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
import teacher from '@/api/hscs/teacherApi'

export default {
    data() {
      return {
           list: null, // 查询结果返回的集合
           page: 1,
           limit: 10,
           total: 0,
           teacherQuery: {},// 条件封装对象
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
        if (this.$route.params && this.$route.params.teacherId) {
         // console.log('出发了');
        // 从路径获取id
          const teacherId = this.$route.params.teacherId
          this.getInfo(teacherId)
        } else { // 如果是添加记录先清空表单数据
          this.teacher = {}
        }
      },
      getList(page = 1) { // 讲师列表的方法
        this.page = page // 点击第二页时传入2
        teacher.getTeacherList(this.page, this.limit)
          .then(response => { // 请求成功返回数据
            this.list = response.data.teacherList
           // console.log(this.list);
            this.total = response.data.total
            // console.log(response)
            // console.log(this.list)
          })
    },
    pageTeacherCondition(page = 1) { // 条件查询
      this.page = page
      console.log(this.teacherQuery);
      teacher.getTeacherListPage(this.page, this.limit, this.teacherQuery)
          .then(response => {
            this.list = response.data.teacherList
            this.total = response.data.total
            // console.log(response)
          // console.log('条件查询返回：' + this.list)
          })
    },
    resetData() { // 清空表单方法
      // 利用双向绑定
      this.teacherQuery = {}
      // 查询所有
      this.getList()
    },
    removeDataById(id) { // 删除讲师
      this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        teacher.deleteTeacher(id) // 删除方法
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