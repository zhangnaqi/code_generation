<template>
  <div class="index">
    <div>
      <h1>配置数据库</h1>
      <el-form 
        inline 
        ref="dbFormRef" 
        :rules="dbFormRule" 
        :model="dbInfo"
      >
        <el-form-item prop="username" label="用户名" required>
          <el-input
            v-model="dbInfo.username" 
            placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item prop="password" label="密码" required>
          <el-input
            v-model="dbInfo.password" 
            placeholder="请输入密码" />
        </el-form-item>
        <el-form-item prop="url" label="数据库地址" required>
          <el-input
            v-model="dbInfo.url" 
            placeholder="请输入数据库地址" />
        </el-form-item>
        <el-form-item>
          <el-button 
            type="primary" 
            @click="dbSubmit"
          >测试数据库连接</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div>
      <h1>配置基本信息</h1>
      <el-form 
        inline 
        ref="globalConfigFormRef" 
        :rules="globalConfigFormRule" 
        :model="globalConfigInfo"
      >
        <el-form-item prop="packageName" label="包名" required>
          <el-input
            v-model="globalConfigInfo.packageName" 
            placeholder="请填写项目包名" />
        </el-form-item>
        <el-form-item prop="templatesType" label="选择生成项目类别" required>
            <el-select v-model="globalConfigInfo.templatesType" placeholder="选择生成项目类别">
              <el-option
                v-for="item in templateTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
             </el-select>
        </el-form-item>
        <el-form-item prop="packageName" label="表需要去除的前缀">
          <el-input
            v-model="globalConfigInfo.tablePrefix" 
            placeholder="请填写表需要去除的前缀" />
        </el-form-item>
        <el-form-item prop="packageName" label="表需要去除的后缀">
          <el-input
            v-model="globalConfigInfo.tableSuffix" 
            placeholder="请填写表需要去除的后缀" />
        </el-form-item>
        <el-form-item>
          <el-button 
            type="primary" 
            @click="globalConfigSubmit"
          >配置</el-button>
        </el-form-item>
        <el-form-item>
          <el-button 
            type="primary" 
            @click="globalConfigReset"
          >重新配置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div>
      <h1>数据库表处理</h1>
      <el-table
        ref="dbTableRef"
        :data="dbTableInfo"
        style="width: 100%"
        @selection-change="handleSelectionDBTableChange"
      >
        <el-table-column type="selection" />
        <el-table-column 
          property="columnsPrefix" 
          label="表字段需去除前缀">
          <template #default="scope">
            <el-input 
              v-model="scope.row.columnsPrefix" 
              placeholder="请输入表字段需去除前缀" />
          </template>
        </el-table-column>
        <el-table-column 
          property="columnsSuffix" 
          label="表字段需去除后缀">
          <template #default="scope">
            <el-input 
              v-model="scope.row.columnsSuffix" 
              placeholder="请输入表字段需去除后缀" />
          </template>
        </el-table-column>
        <el-table-column 
          property="controllerName" 
          label="控制层页面名">
          <template #default="scope">
            <el-input 
              v-model="scope.row.controllerName" 
              placeholder="请输入控制层页面名" />
          </template>
        </el-table-column>
        <el-table-column 
          property="entityName" 
          label="实体类页面名">
          <template #default="scope">
            <el-input 
              v-model="scope.row.entityName" 
              placeholder="请输入实体类页面名" />
          </template>
        </el-table-column>
        <el-table-column 
          property="mapperName" 
          label="dao页面名">
          <template #default="scope">
            <el-input 
              v-model="scope.row.mapperName" 
              placeholder="请输入实体类页面名" />
          </template>
        </el-table-column>
        <el-table-column 
          property="serviceName" 
          label="服务层页面名">
          <template #default="scope">
            <el-input 
              v-model="scope.row.serviceName"
              placeholder="请输入服务层页面名" />
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div>
      <h1>路径配置与文件生成</h1>
      <el-form 
        inline 
        ref="generateCodeFormRef" 
        :rules="generateCodeFormRule" 
        :model="generateCodeInfo"
      >
        <el-form-item prop="path" label="路径" required>
          <el-input
            v-model="generateCodeInfo.path" 
            placeholder="请填写绝对路径" />
        </el-form-item>
        <el-form-item>
          <el-button 
            type="primary" 
            @click="generateCodeSubmit"
          >生成代码文件</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { reactive, ref, toRefs } from 'vue';
import request from '@/utils/request'
import { ElMessage } from 'element-plus';
export default {
  name: 'IndexView',
  setup() {
    /**
     * 数据库
     */
    const dbFormRef = ref()
    const dbFormRule = reactive({
      username: [{ required: true, message: '必填', trigger: 'blur' }],
      password: [{ required: true, message: '必填', trigger: 'blur' }],
      url: [{ required: true, message: '必填', trigger: 'blur' }],
    })
    const dbInfo = reactive({
      username: 'root',
      password: '@ZNQRoot123456',
      url: 'jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai'
    })
    const dbSubmit = () => {
      if (!dbFormRef.value) return
      dbFormRef.value.validate((valid) => {
        if (valid) {
          console.log('submit!')
          console.log("dbInfo", dbInfo)
          request.post('/db/connectTest', dbInfo).then(res => {
            console.log("测试数据库连接", res.data);
            ElMessage.success("连接成功！")
          }).catch(e => {
            ElMessage.error("连接失败！")
          })
        } else {
          console.log('error submit!')
          return false
        }
      })
    }
    /**
     * 表中数据
     */
    const dbTable = reactive({
      dbTableInfo: [],
      dbTableReturn: []
    })
    const handleSelectionDBTableChange = (val) => {
      console.log("选择", val);
      dbTable.dbTableReturn = val
    }
    /**
     * 全局配置
     */
    const globalConfigFormRef = ref()
    const globalConfigFormRule = reactive({
      packageName: [{ required: true, message: '必填', trigger: 'blur' }]
    })
    const globalConfigInfo = reactive({
      packageName: 'com.znq.test',
      tablePrefix: '',
      tableSuffix: '',
      templatesType: ''
    })
    const globalConfigSubmit = () => {
      if (!globalConfigFormRef.value) return
      dbFormRef.value.validate((dbValid) => {
        globalConfigFormRef.value.validate((gcValid) => {
          if (dbValid && gcValid) {
            console.log('submit!')
            console.log("globalConfigInfo", globalConfigInfo)
            request.post('/config/setting', 
              {
                dataBase: dbInfo, 
                globalConfig: globalConfigInfo
              }
            ).then(res => {
              console.log("获取数据库详细信息", res.data);
              dbTable.dbTableInfo = res.data
              dbTable.dbTableReturn = res.data
              // ElMessage.success("连接成功！")
            }).catch(e => {
              ElMessage.error("获取数据库详细信息失败", e)
            })
          } else {
            console.log('error submit!')
            return false
          }
        })
      })
    }
    const globalConfigReset = () => {
      // 关闭数据库，清空表单
      request.post('/db/close').then(res => {
        console.log("关闭数据库，清空表单", res);
        dbFormRef.value.resetFields()
        globalConfigFormRef.value.resetFields()
      }).catch(e => {
        console.log("关闭数据库,清空表单失败", e);
        if (e.message === "未有数据库连接") {
          dbFormRef.value.resetFields()
          globalConfigFormRef.value.resetFields()
        } else ElMessage.error("重置配置失败")
        
      })
    }
    /**
     * 数据生成
     */
    const generateCodeFormRef = ref()
    const generateCodeFormRule = reactive({
      path: [{ required: true, message: '必填', trigger: 'blur' }]
    })
    const generateCodeInfo = reactive({
      path: ''
    })
    const generateCodeSubmit = () => {
      generateCodeFormRef.value.validate((valid) => {
        if(valid) {
          const path = generateCodeInfo.path.replace(/\\/g, '-')
          request.post(
            `/generateCode/${path}`, 
            dbTable.dbTableReturn
          ).then(res => {
            console.log("自动文件生成成功");
            ElMessage.success("生成成功")
          }).catch(e => {
            console.log("自动文件生成失败");
            ElMessage.error('生成失败')
          })
        }
      })
    }
    // 项目生成类别模板选择
    const templateTypeList = [
      {
        value: 'mybatis',
        lable: 'mybatis'
      },
      {
        value: 'mybatis-plus',
        lable: 'mybatis-plus'
      }
    ]
    return {
      dbInfo,
      dbFormRef,
      dbFormRule,
      dbSubmit,
      ...toRefs(dbTable),
      handleSelectionDBTableChange,
      globalConfigInfo,
      globalConfigFormRef,
      globalConfigFormRule,
      globalConfigSubmit,
      globalConfigReset,
      generateCodeFormRef,
      generateCodeFormRule,
      generateCodeInfo,
      generateCodeSubmit,
      templateTypeList
    }
  }
}
</script>

<style lang="scss" scoped>

</style>