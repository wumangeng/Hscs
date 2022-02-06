<template>
  <div class="app-container">
    <!-- 上传 -->
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">excel模版格式说明</el-tag>
        <el-tag>
          <i class="el-icon-download"/>
          <a :href="'/static/1.xlsx'">点击下载模版</a>
        </el-tag>

      </el-form-item>

      <el-form-item label="选择Excel">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="fileUploadSuccess"
          :on-error="fileUploadError"
          :disabled="importBtnDisabled"
          :limit="1"
          :action="BASE_API+'/edu/subject/addSubject'"
          name="file"
          accept="application/vnd.ms-excel">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button
            :loading="loading"
            style="margin-left: 10px;"
            size="small"
            type="success"
            @click="submitUpload">{{ fileUploadBtnText }}</el-button>
       </el-upload>
      </el-form-item>
    </el-form>

    <!-- 列表 -->
    <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;" />
    <el-tree
      ref="tree2"
      :data="data2"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all
    />

  </div>
</template>

<script>
import subject from '@/api/hscs/subject'

export default {

  data() {
    return {
       BASE_API: process.env.BASE_API, // 接口API地址
       OSS_PATH: process.env.OSS_PATH, // 阿里云OSS地址
       fileUploadBtnText: '确认上传', // 按钮文字
       importBtnDisabled: false, // 按钮是否禁用,
       loading: false,

      filterText: '',
      data2: [],
      defaultProps: {
        children: 'children',
        label: 'title'
      }
    }
  },
  created() {
    this.getAllSubjectList()
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },

  methods: {
    //-------------------------------------- 上传
     // 点击按钮上传文件到接口 表单提交
        submitUpload() {
            this.importBtnDisabled = true
            this.loading = true
            this.$refs.upload.submit()
        },
        // 上传成功
        fileUploadSuccess() {
            // 提示信息
            this.loading = false
            this.$message({
                type: 'success',
                message: '添加课程分类成功'
            })
            // 刷新课程分类列表
            subject.getSubjectList()
                .then(respone => {
                  this.data2 = respone.data.list
                })
        },
        // 上传失败
        fileUploadError() {
            this.loading = false
            this.$message({
                type: 'error',
                message: '添加课程分类失败'
            })
        },
    //-------------------------------------- 列表
    getAllSubjectList(){
      subject.getSubjectList()
          .then(respone => {
            this.data2 = respone.data.list
          })
    },
    filterNode(value, data) {
      if (!value) return true
      return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
    }
  }
}
</script>

