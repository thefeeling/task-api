import { API_PATH } from '../enviornment.js';

const JOIN_PATH = `${API_PATH}/v1/users/join`;

/**
 * TODO: 회원가입 컴포넌트 리팩토링
 */
export default {
    name: 'authentication',
    template: `
            <sui-modal v-if="type =='login' " v-model="open">
              <sui-modal-header>로그인</sui-modal-header>
              <sui-modal-content image>
                <sui-modal-description>
                <div class="centered grid">
                    <div class="column">
                        <sui-form>
                            <sui-form-field>
                              <label>E-MAIL</label>
                              <input v-model="email" placeholder="E-MAIL" >
                            </sui-form-field>
                            <sui-form-field>
                              <label>PASSWORD</label>
                              <input type="password" v-model="password" placeholder="PASSWORD" >
                            </sui-form-field>
                            <sui-form-field></sui-form-field>
                            <sui-button type="button" @click.prevent="submitLogin(email, password)">Submit</sui-button>
                            <sui-button type="button" @click.prevent="changeModal('join')">회원가입</sui-button>
                        </sui-form>
                    </div>
                </div>
                </sui-modal-description>
              </sui-modal-content>
            </sui-modal>
            
            <sui-modal v-else v-model="open">
              <sui-modal-header>회원가입</sui-modal-header>
              <sui-modal-content image>
                <sui-modal-description>
                <div class="centered grid">
                    <div class="column">
                        <sui-form>
                            <sui-form-field>
                              <label>E-MAIL</label>
                              <input v-model="join.email" placeholder="E-MAIL" >
                            </sui-form-field>
                            <sui-form-field>
                              <label>PASSWORD</label>
                              <input type="password" v-model="join.password" placeholder="PASSWORD" >
                            </sui-form-field>
                            <sui-form-field></sui-form-field>
                            <sui-button type="button" @click.prevent="submitJoin(join.email, join.password)">가입하기</sui-button>
                            <sui-button type="button" @click.prevent="changeModal('login')">돌아가기</sui-button>
                        </sui-form>
                    </div>
                </div>
                </sui-modal-description>
              </sui-modal-content>
            </sui-modal>                    
    `,
    props: {
        email: String,
        password: String,
        submitLogin: Function
    },
    data() {
        return {
            type: 'login',
            join: {
                email: null,
                password: null,
            }
        }
    },
    methods: {
        async submitJoin(email, password) {
            try {
                const { status, data = { errors: [] } } = await axios.post(`${JOIN_PATH}`, {
                    email, password
                });
                alert("회원가입에 성공했습니다.");
                this.join = {
                    email: null,
                    password: null,
                };
                this.changeModal('login');
            } catch ({ response = {} }) {
                alert(((response.data || {}).errors || []).map(({ reason = '' }) => reason).join("\n"));
            }
        },
        changeModal(type) {
            this.type = type;
        }
    }
}