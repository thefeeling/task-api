export default {
    name: 'pagination',
    template: `
        <div class="ui pagination menu" :class="className" v-show="isShow">
            <a class="item" v-if="isShowEdge" :class="{disabled: currentPage === 1}" @click.prevent="onChange(1)">
                {{previousText}}
            </a>
            <a class="item" v-for="page in pages" :class="{active: page === currentPage}" @click.prevent="onChange(page)">
                {{page}}
            </a>
            <a class="item" v-if="isShowEdge" :class="{disabled: currentPage === totalPages}" @click.prevent="onChange(totalPages)">
                {{nextText}}
            </a>
        </div>
    `,
    props: {
        floated: Boolean,
        alwaysShowEdge: Boolean,
        perPage: {
            type: Number,
            default: 10
        },
        total: {
            type: Number,
            require: true
        },
        currentPage: {
            type: Number,
            require: true
        },
        previousText: {
            type: String,
            default: '«'
        },
        nextText: {
            type: String,
            default: '»'
        },
        pageSize: {
            type: Number,
            default: 10
        }
    },
    computed: {
        className() {
            let cx = [];
            if (this.floated) {
                cx.push('right', 'floated')
            }
            return cx
        },
        totalPages() {
            // calculate total pages
            return Math.ceil(this.total / this.perPage)
        },
        pages() {
            const totalPages = this.totalPages;
            const currentPage = this.currentPage;
            const pageSize = this.pageSize;
            const pageCeil = Math.ceil(pageSize / 2);
            const isOddPageSize = pageSize % 2 === 0;

            let startPage, endPage;
            if (totalPages <= pageSize) {
                startPage = 1;
                endPage = totalPages
            } else {
                if (currentPage <= pageCeil) {
                    startPage = 1;
                    endPage = pageSize
                } else if (currentPage + pageCeil - 1 >= totalPages) {
                    startPage = totalPages - pageSize + 1;
                    endPage = totalPages
                } else {
                    startPage = currentPage - pageCeil + (isOddPageSize ? 0 : 1);
                    endPage = currentPage + pageCeil - 1
                }
            }
            const length = endPage - startPage + 1;
            return Array.from({length}, (v, i) => i + startPage)
        },
        isShow() {
            return this.totalPages > 1
        },
        isShowEdge() {
            return this.alwaysShowEdge || this.totalPages > this.pageSize
        }
    },
    data() {
        return {
            startPage: 0,
            endPage: 0
        }
    },
    methods: {
        onChange(page) {
            if (page === this.currentPage)
                return;
            this.$emit('change', page);
        }
    }
}