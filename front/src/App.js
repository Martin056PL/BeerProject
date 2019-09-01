import React from 'react'
import { BrowserRouter as Router, Route, Link } from "react-router-dom"

import AppBar from './router/AppBar'
import Drawer from './router/Drawer'


const App = props => {
  return (
    <Router>
      <AppBar />
      <Drawer />
    </Router>
  )
}

export default App