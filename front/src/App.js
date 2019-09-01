import React from 'react'
import { BrowserRouter as Router } from "react-router-dom"

import AppBar from './router/AppBar'
import Drawer from './router/Drawer'
import FullScreenCircuralProgress from './components/FullScreenCircuralProgress'


const App = props => {
  return (
    <div>
      <Router>
        <AppBar />
        <Drawer />
      </Router>
      <FullScreenCircuralProgress />
    </div>
  )
}

export default App