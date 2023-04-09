Vue.component("editor",{
	data:function(){
		return {
			editor: ''
		}
	},
	props: ['content', 'path'],
	mounted() {
        const editor = new WangEditor('editor')
        editor.config.menus = ['source', '|', 'bold', 'underline', 'italic', 'strikethrough', 'eraser', 'forecolor', 'bgcolor', '|', 'quote', 'fontfamily', 'fontsize', 'head', 'unorderlist', 'orderlist', 'alignleft', 'aligncenter', 'alignright',
            '|', 'link', 'unlink', 'table', 'img', 'video', 'insertcode', '|', 'undo', 'redo', 'fullscreen'
        ]
        editor.config.uploadImgUrl = this.path
        editor.create()
        this.editor = editor
    },
	methods: {
		result() {
            this.$emit('input', this.editor.$txt.html())
        }
	},
	template:'<div v-html="content" @input="result" id="editor"></div>'
});
