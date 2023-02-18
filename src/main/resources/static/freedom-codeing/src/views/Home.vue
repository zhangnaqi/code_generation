<template>
  <div class="common-layout">
    <el-container>
      <el-header style="padding: 0">
        <el-menu
            class="el-menu-demo"
            mode="horizontal"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
            @select="handleSelect"
        >
            <el-menu-item 
              style="cursor: auto; opacity: 1; font-weight: bold;"
              index="logo" 
              disabled
            >
              代码自动生成系统
            </el-menu-item>
        </el-menu>
      </el-header>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { ref } from 'vue'
export default {
  name: 'HomeView',
  setup() {
    const handleSelect = (key, keyPath) => {
      console.log(key, keyPath)
    }
    return {
      handleSelect
    }
  }

}

</script>

<style lang="scss" scoped>
</style>
<style>
body {
  overflow: scroll;
  height: 100vh;
}
</style>