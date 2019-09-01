import React from 'react'
import ReactDOM from 'react-dom'

import { Provider } from 'react-redux'
import { store } from './store'

import App from './App'
import FullScreenCircuralProgress from './components/FullScreenCircuralProgress'
import Snackbars from './components/Snackbars'

import './main.css'

ReactDOM.render(
  <Provider store={store}>
    <App />
    <FullScreenCircuralProgress />
    <Snackbars />
  </Provider>,
  document.getElementById('root')
)