package com.is.libbase.config;

public class ARoutePath {


    public static class Main {
        /**
         * 对应app主模块
         */
        private static final String MAIN = "/main";

        //对应MainActivity的路径
        public static final String ACTIVITY_MAIN = MAIN + "/mainActivity";
    }

    public static class Home {
        private static final String HOME = "/home";
        public static final String FRAGMENT_HOME = HOME + "/HomeFragment";

    }

    public static class Find {
        private static final String FIND = "/find";
        public static final String FRAGMENT_FIND = FIND + "/FindFragment";
        public static final String ACTIVITY_CATEGORY_DETAIL= FIND + "/CategoryDetailActivity";
        public static final String ACTIVITY_THEME_LIST= FIND + "/ThemeListActivity";
        public static final String ACTIVITY_TOPIC= FIND + "/TopicActivity";
        public static final String KEY_CATEGORY_DATA ="KEY_CATEGORY_DATA";
    }

    public static class User {
        private static final String USER = "/user";
        public static final String FRAGMENT_USER = USER + "/UserFragment";
        //登录界面
        public static final String ACTIVITY_LOGIN = USER + "/LoginActivity";
        //设置界面
        public static final String ACTIVITY_SETTINGS = USER + "/SettingsActivity";
        //用户协议
        public static final String ACTIVITY_AGREEMENT = USER + "/AgreementActivity";
        //账号与绑定
        public static final String ACTIVITY_ACCOUNT = USER + "/AccountActivity";
        //
        public static final String ACTIVITY_EDITINFO = USER + "/EditInfoActivity";
        public static final String ACTIVITY_PERMISSION = USER + "/PermissionActivity";
        public static final String ACTIVITY_PLAYSETTINGS = USER + "/PlaySettingsActivity";
        public static final String ACTIVITY_PUSHSETTINGS = USER + "/PushSettingsActivity";
        public static final String ACTIVITY_RESETPWD = USER + "/ResetpwdActivity";
        public static final String ACTIVITY_ABOUT_ME = USER + "/AboutMeActivity";

        public static final String ACTIVITY_COLLECT = USER + "/CollectActivity";
        public static final String ACTIVITY_CAMERA = USER + "/CameraActivity";
    }

    public static class Plaza {
        private static final String PLAZA = "/plaza";
        public static final String FRAGMENT_PLAZA = PLAZA + "/PlazaFragment";
        public static final String IMAGE_ACTIVITY = PLAZA + "/ImageActivity";
        public static final String KEY_IMAGE_DATA = "KEY_IMAGE_DATA";
    }

    public static class Video {
        private static final String VIDEO = "/VideoDetail";
        public static final String ACTIVITY_VIDEODETAIL = VIDEO + "/VideoDetailActivity";
        public static final String ACTIVITY_SEARCH= VIDEO + "/SearchActivity";
        public static final String ACTIVITY_RECORD = VIDEO + "/RecordActivity";
        public static final String KEY_VIDEO_ID = "KEY_VIDEO_ID";
        public static final String FRAGMENT_INTRODUCE =VIDEO+"/IntroduceFragment";
        public static final String FRAGMENT_COMMENT =VIDEO+"/CommentFragment";
        public static final String FRAGMENT_VIDEO_LIST =VIDEO+"/VideoListFragment";
        public static final String FRAGMENT_CATEGORY_LIST =VIDEO+"/CategoryListFragment";

        public static final String KEY_VIDEO_LIST_TYPE ="KEY_VIDEO_LIST_TYPE";

        //如果style为true，表示需要把列表页的item文本颜色改为白色
        public static final String KEY_VIDEO_LIST_STYLE ="KEY_VIDEO_LIST_STYLE";
        //视频列表-推荐页
        public static final int VIDEO_LIST_FRAGMENT_RECOMMEND = 0;
        //视频列表-日报页
        public static final int VIDEO_LIST_FRAGMENT_DAILY = 1;
        public static final String KEY_CATEGORY_ID = "KEY_CATEGORY_ID";

        public static final String KEY_VIDEO_CATEGORY_TYPE ="KEY_VIDEO_CATEGORY_TYPE";
        //视频列表-推荐
        public static final int CATEGORY_FRAGMENT_RECOMMEND = 0;
        //视频列表-最新
        public static final int CATEGORY_FRAGMENT_NEWPUBLISH = 1;
    }
}
