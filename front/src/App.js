import React from 'react'
import { BrowserRouter as Router, Route } from "react-router-dom"
import { MuiThemeProvider } from '@material-ui/core/styles'
import theme from './theme'

import AppBar from './router/AppBar'
import Drawer from './router/Drawer'
import List from './components/List';



const App = props => {
  return (
    <div>
      <MuiThemeProvider theme={theme}>
        <Router>
          <AppBar />
          <Drawer />
          <Route path="/breweries" component={List} />
        </Router>
      </MuiThemeProvider>
    </div>
  )
}

export default App