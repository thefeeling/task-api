import { API_PATH } from '../enviornment.js';

const USER_KEYWORD_SEARCH_PATH = `${API_PATH}/v1/search/user-search-keywords`;

export default {
    name: 'user-keyword-search',
    template: `
        <div>
            <div class="ui category search" style="margin-bottom: 1rem">
                <sui-dropdown placeholder="CATEGORY" selection :options="categories" v-model="category" />
                <div class="ui icon input">
                    <input class="prompt" v-model="keyword" type="text" placeholder="검색키워드">
                    <i class="search icon"></i>
                </div>
                <sui-button color="blue" @click.prevent="doSearch" content="검색" basic/>
            </div>
            
            <div v-if="items.length > 0">
              <sui-table celled>
                <sui-table-header>
                  <sui-table-row>
                    <sui-table-header-cell>ID</sui-table-header-cell>
                    <sui-table-header-cell>카테고리</sui-table-header-cell>
                    <sui-table-header-cell>키워드</sui-table-header-cell>
                    <sui-table-header-cell>검색일자</sui-table-header-cell>
                  </sui-table-row>
                </sui-table-header>
            
                <sui-table-body>
                  <sui-table-row v-for="(item, index) in items">
                    <sui-table-cell>{{ item.id }}</sui-table-cell>
                    <sui-table-cell>{{ item.category }}</sui-table-cell>
                    <sui-table-cell>{{ item.keyword }}</sui-table-cell>
                    <sui-table-cell>{{ item.searchedAt }}</sui-table-cell>
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
            <div v-else></div>
        </div>
    `,
    data () {
        return {
            items: [],
            currentPage: 1,
            total: 100,

            keyword: '',
            category: 'BOOK',
            categories: [
                { key: 'BOOK', value: 'BOOK', text: '책' },
                { key: 'USER', value: 'USER', text: '회원' },
            ]
        }
    },
    methods: {
        async callApi({
            keyword,
            category = 'BOOK',
            page = 1,
            size = 10,
        } = {}) {
            const { accessToken } = JSON.parse(localStorage.getItem('USER_TOKEN') || '{}');
            if (!accessToken) return;
            const params = {
                ...(keyword && { keyword: keyword.trim() }),
                ...(category && { category: category.trim() }),
                page: page - 1,
                size
            };
            const { data = {} } = await axios.get(`${USER_KEYWORD_SEARCH_PATH}`, {
                headers: {'Authorization': `Bearer ${accessToken}`},
                params,
            });
            const { content = [], number = 0, totalElements = 10 } = data;
            this.items = content;
            this.currentPage = page;
            this.total = totalElements;
        },
        changePage(page) {
            this.callApi({
                keyword: this.keyword,
                category: this.category,
                page,
            });
        },
        doSearch() {
            this.callApi({
                keyword: this.keyword,
                category: this.category,
            });
        }
    },
    created() {
        this.callApi();
    }
}