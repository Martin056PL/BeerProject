import React from 'react'

import { connect } from 'react-redux'
import { drawerOpenActionCreator } from '../../state/drawer'
import { drawerCloseActionCreator } from '../../state/drawer'

import SwipeableDrawer from '@material-ui/core/SwipeableDrawer'

const Drawer = props => {
  return (
    <div>
      <SwipeableDrawer
        open={props._isOpen}
        onClose={props._close}
        onOpen={props._open}
      >
        <p style={{ padding: 5 }}>Tu będzie jakieś menu</p>
      </SwipeableDrawer>
    </div>
  )
}

const mapStateToProps = state => ({
  _isOpen: state.drawer.open
})

const mapDispatchToProps = dispatch => ({
  _open: () => dispatch(drawerOpenActionCreator()),
  _close: () => dispatch(drawerCloseActionCreator())
})

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Drawer)