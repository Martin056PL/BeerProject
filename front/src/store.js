import { createStore, combineReducers, compose, applyMiddleware } from 'redux'
import thunk from 'redux-thunk'

import drawer from './state/drawer'
import fullScreenCircuralProgress from './state/fullScreenCircuralProgress'

const reducer = combineReducers({
  drawer,
  fullScreenCircuralProgress
})

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose

export const store = createStore(
  reducer,
  composeEnhancers(
    applyMiddleware(thunk)
  )
)