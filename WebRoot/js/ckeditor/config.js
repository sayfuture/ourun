/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// 图片上传配置
	config.filebrowserUploadUrl = 'upload.do?type=File';
	config.filebrowserImageUploadUrl = 'upload.do?type=Image';
	config.filebrowserFlashUploadUrl = 'upload.do?type=Flash';
	config.forcePasteAsPlainText = true;
	config.toolbarCanCollapse =true;
	// 图片浏览配置
	config.filebrowserImageBrowseUrl = 'browerServer.do?type=image';
};
