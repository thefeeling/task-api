import { API_PATH } from '../enviornment.js';



export default {
    name: 'book-search',
    template: `
        <div>
            <div class="ui category search" style="margin-bottom: 1rem">
              <div class="ui icon input">
                <input class="prompt" v-model="keyword" type="text" placeholder="책 이름">
                <i class="search icon"></i>
              </div>
              
                <sui-button color="blue" @click.prevent="doSearch(keyword)" content="검색" basic/>
            </div>
            
            <div v-if="items.length > 0">
              <sui-table celled>
                <sui-table-header>
                  <sui-table-row>
                    <sui-table-header-cell>제목</sui-table-header-cell>
                    <sui-table-header-cell>출판사</sui-table-header-cell>
                    <sui-table-header-cell>저자</sui-table-header-cell>
                    <sui-table-header-cell>상세정보</sui-table-header-cell>
                  </sui-table-row>
                </sui-table-header>
            
                <sui-table-body>
                  <sui-table-row v-for="(item, index) in items">
                    <sui-table-cell>{{ item.title }}</sui-table-cell>
                    <sui-table-cell>{{ item.publisher }}</sui-table-cell>
                    <sui-table-cell>{{ item.author.join(',') }}</sui-table-cell>
                    <sui-table-cell style="text-align: center">
                        <sui-button color="blue" @click.native="toggle(item)" content="상세정보" basic/>
                    </sui-table-cell>
                  </sui-table-row>
                </sui-table-body>
                
                <sui-table-footer>
                  <sui-table-row>
                    <sui-table-header-cell colspan="4">
                        <div style="text-align: center">
                            <pagination :current-page="currentPage" :total="total" @change="changePage" :page-size="10"></pagination>                
                        </div>
                    </sui-table-header-cell>
                  </sui-table-row>
                  
                </sui-table-footer>
              </sui-table>            
            </div>
            
            <sui-modal v-model="open">
                <sui-modal-header>상세정보</sui-modal-header>
                <sui-modal-content image>
                <sui-image wrapped size="medium" :src="modal.image" />
                <sui-modal-description>
                    <sui-header>{{ modal.title }}</sui-header>
                    <p>ISBN: {{ modal.isbn }}</p>
                    <p>판매가격: {{ modal.price }}</p>
                    <p>할인가격: {{ modal.salePrice }}</p>
                    <p>출판사: {{ modal.publisher }}</p>
                    <p>저자: {{ (modal.author || []).join(',') }}</p>
                    <p>출판일: {{ moment(modal.publishedAt).format('YYYY-MM-DD').toString() }}</p>
                    <p>설명: {{ modal.description }}</p>
                    <a :href="modal.link">
                        <div class="ui right floated primary button">구매하기<i class="right chevron icon"></i></div>                    
                    </a>
                </sui-modal-description>
                </sui-modal-content>
                <sui-modal-actions>
                    <sui-button positive @click.native="toggle">OK</sui-button>
                </sui-modal-actions>
            </sui-modal>
            <div v-else></div>
        </div>
    `,
    props: {},
    data () {
        return {
            open: false,
            items: [],
            currentPage: 1,
            total: 100,
            keyword: '',

            modal: {}
        }
    },
    methods: {
        toggle(item) {
            this.open = !this.open;
            if (Object.entries(this.modal).length === 0 && this.modal.constructor === Object) {
                this.modal = item;
            } else {
                this.modal = {};
            }
        },
        async changePage(page) {
            if (!this.keyword) {
                this.items = [];
                return;
            }
            const { accessToken } = JSON.parse(localStorage.getItem('USER_TOKEN'));
            if (!accessToken) return;
            const { data = {} } = await axios.get(`${API_PATH}/v1/books`, {
                headers: {'Authorization': `Bearer ${accessToken}`},
                params: { query: this.keyword, page: page - 1 }
            });
            const { content = [], number = 0, totalElements } = data;
            this.items = content;
            this.currentPage = number + 1;
            this.total = totalElements;
        },
        async doSearch(keyword) {
            if (!keyword) {
                this.items = [];
                return;
            }
            const { accessToken } = JSON.parse(localStorage.getItem('USER_TOKEN'));
            if (!accessToken) return;
            const { data = {} } = await axios.get(`${API_PATH}/v1/books`, {
                headers: {'Authorization': `Bearer ${accessToken}`},
                params: { query: keyword }
            });
            const { content = [], number = 0, totalElements } = data;
            this.items = content;
            this.currentPage = number + 1;
            this.total = totalElements;
        }
    },
}