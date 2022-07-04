export default {
  state: {
    isCollapse: false
  },
  mutations: {
    collaspseMenu (state) {
      state.isCollapse = !state.isCollapse
    }
  }
}
