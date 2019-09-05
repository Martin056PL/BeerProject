import { URL } from '../consts'
import { fullScreenCircural } from './fullScreenCircuralProgress'

const ADD_TO_STORE = 'breweries/ADD_TO_STORE'

export const getBreweriesAsyncActionCreator = () => (dispatch) => {
  dispatch(fullScreenCircural.add())
  fetch(URL + '/breweries')
    .then(r => r.json())
    .then(data => {
      const totalPages = data.page.totalPages
      const currentPage = data.page.number
      const breweries = data._embedded.breweries

      dispatch(addToStoreActionCreator(breweries, totalPages, currentPage))
    })
    .finally(() => dispatch(fullScreenCircural.remove()))
}

const addToStoreActionCreator = (breweries, totalPages, currentPage) => ({
  type: ADD_TO_STORE,
  breweries,
  totalPages,
  currentPage
})

const initialState = {
  data: {
    breweriesPage1: ['Perła', 'Warka', 'Tyskie'],
    totalPages: null
  }
}

export default (state = initialState, action) => {
  switch (action.type) {
    case ADD_TO_STORE:
      return {
        ...state,
        data: {
          ...state.data,
          ['breweriesPage' + action.currentPage]: action.breweries,
          totalPages: action.totalPages
        }
      }
    default:
      return state
  }
}