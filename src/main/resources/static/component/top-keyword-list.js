import { API_PATH } from '../enviornment.js';

const TOP_KEYWORD_LIST_PATH = `${API_PATH}/v1/search/top-n-keywords`;

export default {
    name: 'top-keyword-list',
    template: `
        <div>
            <div class="ui category search" style="margin-bottom: 1rem">
                <p>갱신일자: {{ updatedAt }}</p>
            </div>
            
            <div v-if="items.length > 0">
              <sui-table celled>
                <sui-table-header>
                  <sui-table-row>
                    <sui-table-header-cell>순위</sui-table-header-cell>
                    <sui-table-header-cell>키워드</sui-table-header-cell>
                    <sui-table-header-cell>조회수</sui-table-header-cell>
                  </sui-table-row>
                </sui-table-header>
            
                <sui-table-body>
                  <sui-table-row v-for="(item, index) in items">
                    <sui-table-cell>{{ index + 1 }}</sui-table-cell>
                    <sui-table-cell>{{ item.keyword }}</sui-table-cell>
                    <sui-table-cell>{{ item.count }}</sui-table-cell>
                  </sui-table-row>
                </sui-table-body>
              </sui-table>
            </div>
            <div v-else></div>
        </div>
    `,
    data () {
        return {
            items: [],
            updatedAt: '',
        }
    },
    methods: {
        async callApi() {
            const { accessToken } = JSON.parse(localStorage.getItem('USER_TOKEN') || '{}');
            if (!accessToken) return;
            const { data = {} } = await axios.get(`${TOP_KEYWORD_LIST_PATH}`, {
                headers: {'Authorization': `Bearer ${accessToken}`},
            });
            const { list = [], updatedAt } = data;
            this.items = list;
            this.updatedAt = new Date(updatedAt).toLocaleString();
        },
    },
    created() {
        this.callApi();
    }
}