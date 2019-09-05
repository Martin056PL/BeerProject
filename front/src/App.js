import React from 'react'
import { BrowserRouter as Router, Route } from "react-router-dom"
import { MuiThemeProvider } from '@material-ui/core/styles'
import theme from './theme'

import AppBar from './router/AppBar'
import Drawer from './router/Drawer'
import SingleBrewery from './views/SingleBrewery'
import Breweries from './views/Breweries/Breweries';



const App = props => {
  return (
    <div>
      <MuiThemeProvider theme={theme}>
        <Router>
          <AppBar />
          <Drawer />
          <Route path="/breweries/:page" component={Breweries} />
          <Route path="/brewery/:breweryId" component={SingleBrewery} />
        </Router>
      </MuiThemeProvider>
    </div>
  )
}

export default App