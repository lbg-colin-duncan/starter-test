# -*- coding: utf-8 -*-
import sys, os
from recommonmark.parser import CommonMarkParser

project = u'Service Name'
copyright = u'YYYY, John Doe'
version = '1.0'
release = '1.0.0'

# General options
needs_sphinx = '1.0'
master_doc = 'README_TEMPLATE'
pygments_style = 'tango'
add_function_parentheses = True

extensions = ['recommonmark','sphinx_markdown_tables','sphinx.ext.autodoc', 'sphinxcontrib.plantuml']
templates_path = ['_templates']
exclude_trees = ['.build']
source_suffix = {'.md': 'markdown'}
source_encoding = 'utf-8-sig'
source_parsers = {
  '.md': CommonMarkParser
}

# HTML options
html_theme = 'sphinx_rtd_theme'
html_short_title = "my-project"
htmlhelp_basename = 'my-project-doc'
html_use_index = True
html_show_sourcelink = False
html_static_path = ['_static']

# PlantUML options
plantuml = os.getenv('plantuml')