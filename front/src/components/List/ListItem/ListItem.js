import React from 'react'

import MuiListItem from '@material-ui/core/ListItem';
import { ListItemText } from '@material-ui/core';

const ListItem = props => {
  return (
    <MuiListItem alignItems="flex-start">
      <ListItemText
        primary={props.title}
        primaryTypographyProps={{
          color: 'textPrimary'
        }}
      />
    </MuiListItem>
  )
}

export default ListItem