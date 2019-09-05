import React from 'react'

import { connect } from 'react-redux'
import { getBreweriesAsyncActionCreator } from '../../state/breweries'

import List from '../../components/List';

class Breweries extends React.Component {

  componentDidMount() {
    if (this.props._data['']) // dodac warunek ze jezeli nie ma listy z strony z parametru to robi fetcha/
      // na zmiane pagniacji bedzie musial robis ie rerender
      this.props._get()
  }

  render() {
    console.log(this.props)
    return (
      <div>
        <List data={this.props._data['breweriesPage1']} />
      </div>
    )
  }
}

const mapStateToProps = state => ({
  _data: state.breweries.data
})

const mapDispatchToProps = dispatch => ({
  _get: () => dispatch(getBreweriesAsyncActionCreator())
})

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Breweries)