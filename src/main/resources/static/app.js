'use strict';

import authentication from "./component/authentication.js";
import pagination from "./component/pagination.js";
import bookSearch from "./component/book-search.js";
import userKeywordSearch from "./component/user-keyword-search.js";
import topKeywordList from "./component/top-keyword-list.js";
import { API_PATH } from "./enviornment.js";

Vue.use(SemanticUIVue);
Vue.component('authentication', authentication);
Vue.component('pagination', pagination);
Vue.component('book-search', bookSearch);
Vue.component('user-keyword-search', userKeywordSearch);
Vue.component('top-keyword-list', topKeywordList);

const methods = {
    async submitLogin(email, password) {
        const { data = {} } = await axios.post(`${API_PATH}/v1/users/login`, {
            email, password
        });
        const { accessToken } = localStorage.setItem('USER_TOKEN', JSON.stringify(data)) || data;
        console.log(localStorage.getItem('USER_TOKEN'));
        // TODO: accessToken axios interceptor 처리
        const { data: profile } = await axios.get(`${API_PATH}/v1/users/me`, {
            headers: {'Authorization': `Bearer ${accessToken}`},
        });
        this.auth = true;
        this.open = false;
        this.profile = { ...profile };
    },

    async doLogout() {
        localStorage.removeItem('USER_TOKEN');
        this.auth = false;
        this.open = true;
        this.profile = {};
        this.form = {
            email: 'kschoi@dev.io',
            password: 'qwerasdf1234!',
        };
    }
};

const lifeCycles = {
    async created() {
        const token = localStorage.getItem('USER_TOKEN');
        if (!token) return;
        const { accessToken } = JSON.parse(token);
        if (!accessToken) return;
        // TODO: accessToken axios interceptor 처리
        const { data: profile } = await axios.get(`${API_PATH}/v1/users/me`, {
            headers: {'Authorization': `Bearer ${accessToken}`},
        });
        this.auth = true;
        this.open = false;
        this.profile = { ...profile };
    },
};

const INITIAL_STATE = JSON.stringify({
    selectedColor: 'blue',
    auth: false,
    form: {
        email: 'kschoi@dev.io',
        password: 'qwerasdf1234!',
    },
    profile: {},
    open: true
});

const app = new Vue({
    el: '#app',
    data: JSON.parse(INITIAL_STATE),
    ...lifeCycles,
    methods,
    template: `
    <div v-if="!auth">
        <authentication :email="form.email" :password="form.password" :submit-login="submitLogin" />
    </div>

    <div v-else class="ui middle raised very padded container segment">
        <!--  상단 메뉴 -->
        <div class="ui grid">
            <div class="right floated five wide column" style="margin-bottom: 1.5rem">
                <div style="float: right">
                    <sui-button color="red" @click.prevent="doLogout" content="로그아웃" basic/>
                </div>
            </div>
        </div>
        
        <sui-tab :menu="{ color: selectedColor, inverted: true, attached: false, tabular: false }">
            <sui-tab-pane title="책 검색" :attached="false">
                <book-search />
            </sui-tab-pane>
          
            <sui-tab-pane title="조회 키워드 검색" :attached="false">
                <user-keyword-search />
            </sui-tab-pane>
          
            <sui-tab-pane title="인기 키워드 조회" :attached="false">
                <top-keyword-list />
            </sui-tab-pane>
        </sui-tab>
    </div>
`
});

