{{/* vim: set filetype=mustache: */}}
{{/*
Expand the name of the chart.
*/}}
{{- define "api.name" -}}
{{- if .Values.service.version -}}
	{{- $name := default .Chart.Name .Values.service.name | trunc 59 | trimSuffix "-" -}}
	{{printf "%s-%s" $name .Values.service.version -}}
{{- else -}}
	{{- default .Chart.Name .Values.service.name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "api.fullname" -}}
	{{- if .Values.service.name -}}
			{{- if .Values.service.version -}}
				{{- .Values.service.name | trunc 59 | trimSuffix "-" -}}-{{- .Values.service.version -}}
			{{- else -}}
				{{- .Values.service.name | trunc 63 | trimSuffix "-" -}}
			{{- end -}}
	{{- else -}}
		{{- $name := default .Chart.Name .Values.service.name -}}
			{{- if contains $name .Release.Name -}}
				{{- if .Values.service.version -}}
					{{- $name := printf "%s-%s" .Release.Name | trunc 59 | trimSuffix "-" .Values.service.version -}}
				{{- else -}}
					{{- $name := .Release.Name | trunc 63 | trimSuffix "-" -}}
				{{- end -}}
			{{- else -}}
				{{- if .Values.service.version -}}
					{{- $name := printf "%s-%s" .Release.Name $name | trunc 59 | trimSuffix "-" -}}
					{{- $name := printf "%s-%s" $name .Values.service.version -}}
				{{- else -}}
					{{- $name := printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" -}}
				{{- end -}}
			{{- end -}}
	{{- end -}}
{{- end -}}

{{- define "api.labels" -}}
cmdb_appid: {{ .Values.cmdb_appid }}
app.kubernetes.io/name: {{ include "api.name" . }}
helm.sh/chart: {{ include "api.chart" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- if .Values.service.version }}
version: {{ .Values.service.version }}
{{- end }}
{{- end -}}

{{- define "api.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "api.kname" -}}
{{- if .Values.service.version -}}
	{{- .Values.service.name | trunc 59 | trimSuffix "-" -}}-{{- .Values.service.version -}}
{{- else -}}
	{{- if .Values.deployment.version -}}
		{{- .Values.service.name | trunc 63 | trimSuffix "-" -}}
	{{- else -}}
		{{ include "api.name" . }}
	{{- end -}}
{{- end -}}
{{- end -}}

{{- define "selector.labels" -}}
app.kubernetes.io/name: {{ include "api.kname" . }}
{{- if .Values.deployment.version }}
{{- else }}
app.kubernetes.io/instance: {{ .Values.release.name }}
{{- end -}}
{{- end -}}
