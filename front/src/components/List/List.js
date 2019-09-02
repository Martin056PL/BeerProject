import React from 'react'

import ListItem from './ListItem'

import MuiList from '@material-ui/core/List';
import { Divider } from '@material-ui/core';

const breweries = ['Perła', 'Warka', 'Tyskie']

const List = props => {
  return (
    <MuiList>
      {breweries.map(brewery => (
        <div key={brewery}>
          <ListItem title={brewery} />
          <Divider />
        </div>
      ))}
    </MuiList>
  )
}

export default List