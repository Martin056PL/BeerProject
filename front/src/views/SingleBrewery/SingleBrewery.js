import React from 'react'
import { connect } from 'react-redux'

import { withRouter } from 'react-router-dom'
import { Typography } from '@material-ui/core';

class SingleBrewery extends React.Component {
  state = {

  }

  render() {
    return (
      <div>
        <Typography
          variant={'h3'}
          color={'textPrimary'}>
          {this.props.match.params.breweryId[0].toUpperCase() + this.props.match.params.breweryId.slice(1)}
        </Typography>
        <Typography
          variant={'h5'}
          color={'textPrimary'}
        >
          Opis zdjęcie itd...
        </Typography>
      </div>
    )
  }
}

const mapStateToProps = state => ({})

const mapDispatchToProps = dispatch => ({})

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withRouter(SingleBrewery))