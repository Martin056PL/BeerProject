import React from 'react'

import ListItem from './ListItem'
import { withRouter } from 'react-router-dom'

import MuiList from '@material-ui/core/List';
import { Divider } from '@material-ui/core';

const breweries = ['Perła', 'Warka', 'Tyskie']

const List = props => {
  return (
    <MuiList>
      {breweries.map(brewery => (
        <div key={brewery}>
          <ListItem
            title={brewery}
            click={() => props.history.push('/brewery/' + brewery.toLowerCase())}
          />
          <Divider />
        </div>
      ))}
    </MuiList>
  )
}

export default withRouter(List)