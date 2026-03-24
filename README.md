# IsVideo - 短视频社交应用（Android Java）

一个采用 **MVVM + 组件化** 架构开发的短视频社交 App，支持视频自动播放、点赞评论实时更新、模块间通信等完整功能。

**独立开发项目**，2025.12 – 2026.01 完成。APK 体积优化至 **8MB**，滑动帧率稳定 **60fps**。

## 项目地址
[https://github.com/laughzzb/IsVideo](https://github.com/laughzzb/IsVideo)

## 🎯 项目亮点

- **组件化架构**：将 App 拆分为 `home`、`video`、`user` 等独立模块，大幅降低耦合
- **ARouter 路由**：完美解决模块间跳转与通信问题
- **网络封装**：OkHttp + Retrofit2 二次封装（Token 自动刷新、日志拦截器、统一错误处理），开发效率提升约 **30%**
- **短视频播放**：MediaPlayer + 手势控制（播放/暂停/循环/滑动进度）
- **实时互动**：LiveData + DataBinding 实现点赞数、评论数自动刷新
- **跨模块通信**：EventBus 处理视频上传成功后首页列表刷新等场景
- **Android 10+ 适配**：封装 `MediaUtils` 工具类，完美解决 MediaStore + 动态权限 + ContentResolver 兼容性问题
- **性能优化**：
  - Glide 三级缓存 + RecyclerView 预加载
  - SmartRefreshLayout 实现下拉刷新 / 上拉加载
  - LeakCanary 检测并修复多处内存泄漏
  - ViewModel + Lifecycle 严格管理生命周期
- **体积优化**：图片压缩、资源瘦身、ProGuard 混淆，最终安装包仅 **8MB**

---

## 🛠 技术栈

- **语言**：Java
- **架构**：MVVM + DataBinding + 组件化
- **路由**：ARouter
- **网络**：OkHttp + Retrofit2
- **UI**：RecyclerView + DataBinding + SmartRefreshLayout
- **播放器**：MediaPlayer
- **图片加载**：Glide
- **事件通信**：EventBus
- **内存检测**：LeakCanary
- **其他**：ViewModel、Lifecycle、MediaStore、ContentResolver、ProGuard

---

## 📱 核心功能实现

1. **首页视频流**：RecyclerView + 预加载 + 自动播放/暂停（滑动时精准控制）
2. **视频详情页**：手势控制（双击点赞、滑动进度、上下滑动切换视频）
3. **点赞/评论实时更新**：LiveData 驱动 DataBinding，数据变化 UI 自动刷新
4. **视频上传成功后刷新**：EventBus 跨模块消息传递
5. **本地媒体文件读取**：兼容 Android 10+，封装 `MediaUtils` 工具类

---

## 💡 难点与解决方案

- **组件化依赖管理** → 使用 ARouter 实现模块解耦与跳转
- **Token 失效处理** → Retrofit 拦截器自动刷新 + 错误基类统一处理
- **Android 10+ 媒体权限** → MediaStore + 动态权限申请 + ContentResolver 封装
- **内存泄漏** → LeakCanary 全量检测 + ViewModel + Lifecycle 严格管理
- **APK 体积过大** → WebP 压缩 + 资源删除 + ProGuard 规则优化（最终 8MB）

---

## 🚀 如何运行

1. Clone 项目
   ```bash
   git clone https://github.com/laughzzb/IsVideo.git
