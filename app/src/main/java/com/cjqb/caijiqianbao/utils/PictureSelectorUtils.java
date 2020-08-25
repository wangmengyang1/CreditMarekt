package com.cjqb.caijiqianbao.utils;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.application.myApplication;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.ArrayList;
import java.util.List;

import static com.luck.picture.lib.camera.CustomCameraView.BUTTON_STATE_BOTH;
import static com.luck.picture.lib.camera.CustomCameraView.BUTTON_STATE_ONLY_CAPTURE;
import static com.luck.picture.lib.camera.CustomCameraView.BUTTON_STATE_ONLY_RECORDER;


public class PictureSelectorUtils {

    /**
     * 图片单选  裁剪
     *
     * @param activity
     * @param listener
     * .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
     */
    public static void startCropPicSingleSelect(Activity activity, int width, int height, int aspect_ratio_x, int aspect_ratio_y, OnResultCallbackListener<LocalMedia> listener) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(R.style.picture)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(3)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .isSingleDirectReturn(false)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(false)// 图片列表点击 缩放效果 默认true
                .isCompress(true)
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .compressQuality(50)
                .cropImageWideHigh(width, height)
                .withAspectRatio(aspect_ratio_x, aspect_ratio_y)
                .isEnableCrop(true)
                .recordVideoSecond(25)
                .setButtonFeatures(BUTTON_STATE_ONLY_CAPTURE)
                .forResult(listener);

    }

    /**
     * 图片单选
     *
     * @param activity
     * @param listener
     */
    public static void startPicSingleSelect(Activity activity, OnResultCallbackListener<LocalMedia> listener) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(R.style.picture)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(3)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .isSingleDirectReturn(false)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(false)// 图片列表点击 缩放效果 默认true
                .isCompress(true)
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .compressQuality(50)
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .recordVideoSecond(25)
                .setButtonFeatures(BUTTON_STATE_ONLY_CAPTURE)
                .forResult(listener);

    }

    /**
     * 图片单选(相机)
     *
     * @param activity
     * @param listener
     */
    public static void startCameraSelect(Activity activity, OnResultCallbackListener<LocalMedia> listener) {
        PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(R.style.picture)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                .minSelectNum(1)// 最小选择数量
                .maxSelectNum(1)
                .imageSpanCount(3)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .isSingleDirectReturn(false)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(false)// 图片列表点击 缩放效果 默认true
                .isCompress(true)
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .compressQuality(50)
                .recordVideoSecond(25)
                .setButtonFeatures(BUTTON_STATE_ONLY_CAPTURE)
                .forResult(listener);

    }

    /**
     * 视频单选(相机)
     *
     * @param activity
     * @param listener
     */
    public static void startCameraVideoSelect(Activity activity, OnResultCallbackListener<LocalMedia> listener) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofVideo())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(R.style.picture)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .isSingleDirectReturn(false)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isCamera(true)
                .videoMaxSecond(30)
                .videoMinSecond(1)
                .videoQuality(1)
                .isCompress(true)
                .compressQuality(50)
                .recordVideoSecond(25)
                .minimumCompressSize(1000)// 小于100kb的图片不压缩
                .setButtonFeatures(BUTTON_STATE_ONLY_RECORDER)
                .forResult(listener);

    }


    /**
     * 图片多选
     *
     * @param activity
     * @param listener
     */
    public static void startMultiplePicSelect(Activity activity, int num, OnResultCallbackListener<LocalMedia> listener) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(R.style.picture)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                .minSelectNum(1)// 最小选择数量
                .maxSelectNum(num)
                .imageSpanCount(3)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .isSingleDirectReturn(false)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(false)// 图片列表点击 缩放效果 默认true
                .isCompress(true)
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .compressQuality(50)
                .recordVideoSecond(25)
                .setButtonFeatures(BUTTON_STATE_ONLY_CAPTURE)
                .forResult(listener);

    }

    /**
     * 图片多选视频
     *
     * @param activity
     * @param listener
     */
    public static void startMultiplePicVideoSelect(Activity activity, int num, OnResultCallbackListener<LocalMedia> listener) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofAll())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(R.style.picture)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                .minSelectNum(1)// 最小选择数量
                .videoMaxSecond(120)
                .videoMinSecond(1)
                .maxSelectNum(num)
                .maxVideoSelectNum(1)
                .imageSpanCount(3)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .isSingleDirectReturn(false)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isPreviewVideo(true)
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(false)// 图片列表点击 缩放效果 默认true
                .isCompress(true)
                .compressQuality(50)
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .minimumCompressSize(300)// 小于100kb的图片不压缩
                .forResult(listener);

    }


    /**
     * 视频多选
     *
     * @param activity
     * @param listener
     */
    public static void startVideoSelect(Activity activity, int num, OnResultCallbackListener<LocalMedia> listener) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofVideo())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(R.style.picture)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                .minSelectNum(1)// 最小选择数量
                .videoMaxSecond(301)
                .videoMinSecond(1)
                .maxSelectNum(num)
                .imageSpanCount(3)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .isSingleDirectReturn(false)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewVideo(true)
                .isCamera(false)// 是否显示拍照按钮
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .minimumCompressSize(1000)// 小于100kb的图片不压缩
                .recordVideoSecond(25)
                .setButtonFeatures(BUTTON_STATE_ONLY_RECORDER)
                .forResult(listener);

    }


    /**
     * 图片多选
     *
     * @param activity
     * @param listener
     */
    public static void startMultiplePicSelect(Activity activity, int num, List<LocalMedia> localMediaList, OnResultCallbackListener<LocalMedia> listener) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(R.style.picture)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                .minSelectNum(1)// 最小选择数量
                .maxSelectNum(num)
                .imageSpanCount(3)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .isSingleDirectReturn(false)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(false)// 图片列表点击 缩放效果 默认true
                .isCompress(true)
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .compressQuality(50)
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .selectionData(localMediaList)
                .recordVideoSecond(25)
                .setButtonFeatures(BUTTON_STATE_ONLY_CAPTURE)
                .forResult(listener);

    }

    /**
     * 图片单选
     *
     * @param activity
     * @param listener
     */
    public static void startPicSingleSelect(Activity activity, OnResultCallbackListener<LocalMedia> listener, String filePath) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(R.style.picture)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(3)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .isSingleDirectReturn(false)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(false)// 图片列表点击 缩放效果 默认true
                .isCompress(true)
                .compressQuality(50)
                .compressSavePath(filePath)
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .minimumCompressSize(1000)// 小于100kb的图片不压缩
                .recordVideoSecond(25)
                .setButtonFeatures(BUTTON_STATE_ONLY_CAPTURE)
                .forResult(listener);

    }

    /**
     * 图片预览
     */
    public static void startPreviewPic(Activity activity, int position, List<LocalMedia> selectList) {
        PictureSelector.create(activity)
                .themeStyle(R.style.picture) // xml设置主题
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .openExternalPreview(position, selectList);
    }

    /**
     * 图片预览
     */
    public static void startPreviewPicForString(Activity activity, int position, List<String> selectList) {
        List<LocalMedia> localMedia = new ArrayList<>();
        if (selectList!=null&&!selectList.isEmpty()){
            for (String item:selectList){
                LocalMedia media = new LocalMedia();
                media.setPath(item);
                localMedia.add(media);
            }
        }
        PictureSelector.create(activity)
                .themeStyle(R.style.picture) // xml设置主题
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .openExternalPreview(position, localMedia);
    }


    /**
     * 获取图片的路径
     *
     * @param media
     * @return
     */
    public static String getPicPath(LocalMedia media) {
        if (media == null) {
            return "";
        }
        String path;

        if (media.isCut() && !media.isCompressed()) {
            // 裁剪过
            path = media.getCutPath();
        } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
            // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
            path = media.getCompressPath();
        } else {
            // 原图
            path = media.getPath();
        }

        return path;
    }

    /**
     * 获取图片的路径
     *
     * @param media
     * @return
     */
    public static String getUploadPicPath(LocalMedia media) {
        if (media == null) {
            return "";
        }
        String path;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !TextUtils.isEmpty(media.getAndroidQToPath())) {
            path = media.getAndroidQToPath();
        } else {
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();
            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
            } else {
                // 原图
                path = media.getPath();
            }
        }
        if (path!=null&&path.startsWith("content://")) {
            String filePathByUri = UriTool.getRealPathFromUri(myApplication.getContext(), Uri.parse(path));
            if (!TextUtils.isEmpty(filePathByUri)){
                return filePathByUri;
            }else {
                return path;
            }
        }else {
            return path;
        }

    }

    /**
     * 打开相机 、拍照或视频
     */
    public static void startCameraPicVideo(Activity activity, OnResultCallbackListener<LocalMedia> listener) {
        PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofVideo())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(R.style.picture)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                .minSelectNum(1)//// 最小选择数量
                .videoMaxSecond(30)
                .videoMinSecond(1)
                .videoQuality(1)
                .isCompress(true)
                .compressQuality(50)
                .minimumCompressSize(1000)// 小于100kb的图片不压缩
                .recordVideoSecond(25)
                .setButtonFeatures(BUTTON_STATE_BOTH)
                .forResult(listener);

    }


    /**
     * 视频预览
     *
     * @param path
     */
    public static void startPreviewVideo(Activity activity, String path) {
        PictureSelector.create(activity)
                .themeStyle(R.style.picture) // xml设置主题
                .externalPictureVideo(path);
    }
}